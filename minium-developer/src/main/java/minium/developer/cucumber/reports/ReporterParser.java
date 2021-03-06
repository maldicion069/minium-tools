package minium.developer.cucumber.reports;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minium.developer.web.rest.dto.SummaryDTO;
import net.masterthought.cucumber.json.Element;
import net.masterthought.cucumber.json.Feature;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

@Component
public class ReporterParser {

	/**
	 * Parse the results from the generated cucumber report
	 * @param results
	 * @return
	 * @throws JsonSyntaxException
	 * @throws JsonIOException
	 * @throws FileNotFoundException
	 */
	public List<Feature> parseJsonResult(String results) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Feature[] features = new Gson().fromJson(results, Feature[].class);
		return Arrays.asList(features);
	}

	public Map<String, Feature> parseJsonResultSet(String results) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Map<String, Feature> featuresMap = new HashMap<String, Feature>();
		Feature[] features = new Gson().fromJson(results, Feature[].class);
		for (Feature f : features) {
		    featuresMap.put(f.getUri(), f);
		}
		return featuresMap;
	}

	public SummaryDTO getSummaryFromResult(String results) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		SummaryDTO summary;
		int totalScenarios = 0, passingScenarios = 0, failingScenarios = 0;
		List<Feature> features = parseJsonResult(results);
		List<Element> elem  = new ArrayList<Element>();
		for (Feature f : features) {
			f.processSteps();
			totalScenarios   += f.getNumberOfScenarios();
			passingScenarios += f.getNumberOfScenariosPassed();
			failingScenarios += f.getNumberOfScenariosFailed();
		}
		summary = new SummaryDTO(totalScenarios,passingScenarios,failingScenarios,elem);
		return summary;
	}

	public SummaryDTO getSummaryFromFeatures(List<Feature> features) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		SummaryDTO summary;
		int totalScenarios = 0, passingScenarios = 0, failingScenarios = 0;
		List<Element> elem  = new ArrayList<Element>();
		for (Feature f : features) {
			f.processSteps();
			totalScenarios   += f.getNumberOfScenarios();
			passingScenarios += f.getNumberOfScenariosPassed();
			failingScenarios += f.getNumberOfScenariosFailed();
		}
		summary = new SummaryDTO(totalScenarios,passingScenarios,failingScenarios,elem);
		return summary;
	}

}
