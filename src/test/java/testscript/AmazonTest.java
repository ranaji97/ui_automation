package testscript;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import generic.BaseClass;

public class AmazonTest extends BaseClass{
	
	@Test
	   public void searchAndPrintProducts() {
		
		// Search for lg soundbar
		WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));

		String inputSearchString = "lg soundbar";
		searchBox.sendKeys(inputSearchString);
		driver.findElement(By.id("nav-search-submit-button")).click();

		List<WebElement> products = driver.findElements(By.xpath(
				"//div[contains(@class, 's-result-item') and @data-component-type = 's-search-result']//span[contains(@class, 'text-normal')]"));

		List<String> productNames = new ArrayList<>();

		for (WebElement tempProd : products) {

			String productName = tempProd.getAttribute("innerText");
			productNames.add(productName);

		}

//		System.out.println("Product Names :: " + productNames);

		HashMap<String, Integer> productAndPrices = new HashMap<String, Integer>();

		for (String productName : productNames) {

			try {
				WebElement priceOfProductELement = driver.findElement(By.xpath(
						"//div[contains(@class, 's-result-item') and @data-component-type = 's-search-result'][.//span[contains(@class, 'text-normal')][contains(., '"
								+ productName + "')]]//span[contains(@class, 'a-price-whole')]"));

				Integer priceOfTheProduct = Integer.parseInt(priceOfProductELement.getText().replaceAll(",", ""));
				productAndPrices.put(productName, priceOfTheProduct);
				
			} catch (NoSuchElementException e) {
//				System.out.println("Price of Product Not Displayed -->  " + productName);
			}

		}

//		System.out.println("Products and Prices" + productAndPrices);

		HashMap<String, Integer> productAndPricesSortedByPrice = new HashMap<String, Integer>();

		productAndPricesSortedByPrice = sortByValue(productAndPrices);

		for (Map.Entry<String, Integer> entry : productAndPricesSortedByPrice.entrySet()) {

			System.out.println(entry.getValue() + " " + entry.getKey());

		}

		driver.quit();

	}

	// function to sort hashmap by values
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}
}