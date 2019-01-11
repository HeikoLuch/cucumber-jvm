package com.avenqo.cucumber.example.appl.swtbot.steps.calculator;

import java.util.Locale;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;

import java.util.Map;

import com.avenqo.cucumber.example.appl.swtbot.steps.calculator.RpnCalculatorStepdefs.Entry;

import static java.util.Locale.ENGLISH;
public class TypeRegistryConfiguration implements TypeRegistryConfigurer{

	@Override
	public Locale locale() {
		return ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry typeRegistry) {
		typeRegistry.defineDataTableType(new DataTableType( RpnCalculatorStepdefs.Entry.class,
	           new TableEntryTransformer<Entry>() {

				@Override
				public Entry transform(Map<String, String> row) throws Throwable {
					return new Entry(
	                Integer.valueOf(row.get("first")),
	                Integer.valueOf(row.get("second")),
	                row.get("operation")
	            );
				}
			 
//				@Override
//				public Entry transform(DataTable table) throws Throwable {
//					
//					return new Entry(
//			                Integer.valueOf(row.get("first")),
//			                Integer.valueOf(row.get("second")),
//			                row.get("operation")
//			            );
//				}
			
				}
	        ));
		
	}

}
