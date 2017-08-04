package io.myapp.api.repo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import io.myapp.api.entity.Weather;
import io.myapp.api.entity.Wind;
import io.myapp.api.repo.WeatherRepo;

@Repository
public class WeatherRepoImpl implements WeatherRepo {

	@Autowired
	private MongoTemplate mt;

	private DBObject getDBGroup(String average) {

		Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
		dbObjIdMap.put("day", "$day_month");
		dbObjIdMap.put("month", "$month_year");
		dbObjIdMap.put("year", "$year");
		if (average.equals("hourly")) {
			dbObjIdMap.put("hour", "$hour_day");
		}

		DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
		groupFields.put("humidity", new BasicDBObject("$avg", "$humidity"));
		groupFields.put("temperature", new BasicDBObject("$avg", "$temperature"));
		groupFields.put("pressure", new BasicDBObject("$avg", "$pressure"));
		groupFields.put("speed", new BasicDBObject("$avg", "$speed"));
		groupFields.put("degree", new BasicDBObject("$avg", "$degree"));

		DBObject group = new BasicDBObject("$group", groupFields);
		return group;
	}

	private DBObject getDBProject(String average) {

		DBObject projectFields = new BasicDBObject("humidity", "$humidity");
		projectFields.put("temperature", "$temperature");
		projectFields.put("pressure", "$pressure");
		projectFields.put("speed", "$wind.speed");
		projectFields.put("degree", "$wind.degree");
		projectFields.put("day_month", new BasicDBObject("$dayOfMonth", "$timestamp"));
		projectFields.put("month_year", new BasicDBObject("$month", "$timestamp"));
		projectFields.put("year", new BasicDBObject("$year", "$timestamp"));

		if (average.equals("hourly")) {
			projectFields.put("hour_day", new BasicDBObject("$hour", "$timestamp"));
		}

		DBObject project = new BasicDBObject("$project", projectFields);
		return project;
	}

	private DBObject getDBMatch(String city) {
		DBObject match = new BasicDBObject("$match", new BasicDBObject("city", Pattern.compile(".*city.*" , Pattern.CASE_INSENSITIVE)));
		return match;
	}

	private Map<String, Weather> getList(AggregationOutput output) {
		Map<String, Weather> weather = new HashMap<String, Weather>();
		for (DBObject result : output.results()) {
			String timestamp = result.get("_id").toString();
			Weather w = mt.getConverter().read(Weather.class, result);
			Wind wi = new Wind();
			wi.setSpeed((Double) (result.get("speed")));
			wi.setDegree((Double) (result.get("degree")));
			w.setWind(wi);
			weather.put(timestamp, w);
		}
		return weather;
	}

	@Override
	public List<String> findAll() {
		Query query = new Query();
		query.fields().include("city");
		List<Weather> weather = mt.find(query, Weather.class);

		List<String> cities = new ArrayList<String>();
		for (Weather w : weather) {
			String city = w.getCity();
			cities.add(city);
		}
		return cities.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
	}

	@Override
	public Optional<Weather> findOne(String city) {
		Query query = new Query();
		query.addCriteria(Criteria.where("city").regex(city,"i"));
		query.with(new Sort(Sort.Direction.DESC, "timestamp"));
		query.limit(1);
		List<Weather> w = mt.find(query, Weather.class, "weathers");
		if (!w.isEmpty()) {
			return Optional.of(w.get(0));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Weather> findWeatherbyCityAndProperty(String city, String property) {
		Query query = new Query(Criteria.where("city").regex(city,"i"));
		query.with(new Sort(Sort.Direction.DESC, "timestamp")).fields().include(property.toLowerCase());
		query.limit(1);
		return Optional.ofNullable(mt.findOne(query, Weather.class, "weathers"));
	}

	@Override
	public Map<String, Weather> findHourlyWeatherAveragebyCity(String city) {

		List<DBObject> dbobjectList = new ArrayList<DBObject>();
		dbobjectList.add(getDBMatch(city));
		dbobjectList.add(getDBProject("hourly"));
		dbobjectList.add(getDBGroup("hourly"));

		AggregationOutput output = mt.getCollection("weathers").aggregate(dbobjectList);

		Map<String, Weather> weather = getList(output);

		if (!weather.isEmpty()) {
			return weather;
		} else {
			return null;
		}
	}

	@Override
	public Map<String, Weather> findDailyWeatherAveragebyCity(String city) {

		List<DBObject> dbobjectList = new ArrayList<DBObject>();
		dbobjectList.add(getDBMatch(city));
		dbobjectList.add(getDBProject("daily"));
		dbobjectList.add(getDBGroup("daily"));

		AggregationOutput output = mt.getCollection("weathers").aggregate(dbobjectList);

		Map<String, Weather> weather = getList(output);

		if (!weather.isEmpty()) {
			return weather;
		} else {
			return null;
		}
	}

	@Override
	public Weather create(Weather weather) {
		mt.insert(weather);
		return weather;
	}

}
