package com.bestbuy.bestbuyapiinfo;

import com.bestbuy.studentinfo.ProductSteps;
import com.bestbuy.testBase.TestBaseProducts;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductsCRUDTest extends TestBaseProducts {

    static String name = "Prime" + TestUtils.getRandomValue();
    static String type = "Testing";
    static double price = 20;
    static int shipping = 0;
    static String upc = "039800011513";
    static String description = "Automation";
    static String manufacturer = "Prime Testing";
    static String model = "26P";
    static String url = "http://www.bestbuy.com/site/energizer-max-batteries-aa-4-pack/150115.p?id=1051384046217&skuId=150115&cmp=RMXCC";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/1501/150115_sa.jpg";
    static int productID;

    @Steps
    ProductSteps productsSteps;

    @Title("This will create a new product")
    @Test
    public void test001() {
        List<String> courseList = new ArrayList<>();
        courseList.add("Scala");
        courseList.add("Java");
        ValidatableResponse response =productsSteps.createProduct(name,type,price,shipping,upc,description,manufacturer,model,url,image);
        response.log().all().statusCode(201);
    }

    @Title("Verify if the product was added")
    @Test
    public void test002() {

        HashMap<String, Object> value = productsSteps.getProductInfoByName(name);
        Assert.assertThat(value, hasValue(name));
        productID = (int) value.get("id");
    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";
        List<String> courseList = new ArrayList<>();
        courseList.add("Scala");
        courseList.add("Java");
        productsSteps.updateProduct(productID,name,type,price,shipping,upc,description,manufacturer,model,url,image).log().all().statusCode(200);
        HashMap<String, Object> value = productsSteps.getProductInfoByName(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title("Delete the product and verify if the product is deleted!")
    @Test
    public void test004() {
        productsSteps.deleteProduct(productID).statusCode(200);
        productsSteps.getProductById(productID) .statusCode(404);
    }

}
