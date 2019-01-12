package com.avenqo.cucumber.example.appl.swtbot.steps.calculator;

import static java.util.Locale.ENGLISH;

import java.util.Locale;
import java.util.Map;

import com.avenqo.cucumber.example.appl.swtbot.steps.calculator.RpnCalculatorStepdefs.Entry;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry typeRegistry) {
		typeRegistry.defineDataTableType(
				new DataTableType(RpnCalculatorStepdefs.Entry.class, new TableEntryTransformer<Entry>() {

					@Override
					public Entry transform(Map<String, String> row) throws Throwable {
						return new Entry(Integer.valueOf(row.get("first")), Integer.valueOf(row.get("second")),
								row.get("operation"));
					}
				}));
	}
}
