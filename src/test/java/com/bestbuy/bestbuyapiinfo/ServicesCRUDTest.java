package com.bestbuy.bestbuyapiinfo;

import com.bestbuy.studentinfo.ServicesSteps;
import com.bestbuy.testBase.TestBaseServices;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ServicesCRUDTest extends TestBaseServices {

    static String name = "PrimeTesting" + TestUtils.getRandomValue();
    static int serviceID;

    @Steps
    ServicesSteps servicesSteps;

    @Title("This will create a new service")
    @Test
    public void test001() {
        ValidatableResponse response = servicesSteps.createService(name);
        response.log().all().statusCode(201);
    }

    @Title("Verify if the service was added")
    @Test
    public void test002() {

        HashMap<String, Object> value = servicesSteps.getServiceInfoByName(name);
        Assert.assertThat(value, hasValue(name));
        serviceID = (int) value.get("id");
    }

    @Title("Update the service information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";
        servicesSteps.updateService(name, serviceID).log().all().statusCode(200);
        HashMap<String, Object> value = servicesSteps.getServiceInfoByName(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title("Delete the service and verify if the service is deleted!")
    @Test
    public void test004() {
        servicesSteps.deleteService(serviceID).statusCode(200);
        servicesSteps.getServiceByName(serviceID).statusCode(404);

    }

}
