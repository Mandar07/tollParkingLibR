
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeparking.config.PolicyConfig;
import com.modeparking.data.ParkingBill;
import com.modeparking.data.ParkingPolicy;
import com.modeparking.data.ParkingSlot;
import com.modeparking.data.ParkingSlotType;
import com.modeparking.data.Repository.ParkingBillRepository;
import com.modeparking.data.Repository.ParkingSlotRepository;
import com.modeparking.model.IPricingService;
import com.modeparking.model.ISlotService;

import com.modeparking.model.impl.TollParkingAgent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITollParkingController {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ISlotService slotService;

    @MockBean
    private IPricingService pricingService;

    @MockBean
    private TollParkingAgent tollParkingAgent;

    @MockBean
    private ParkingSlotRepository parkingSlotRepository;

    @MockBean
    private ParkingBillRepository parkingBillRepository;

    @Test
    public void testEndpoints() throws Exception {
        ParkingPolicy parkingPolicy = new ParkingPolicy(15.0, 25.0);
        ParkingPolicy updatedParkingPolicy = new ParkingPolicy(12.0, 17.0);
        PolicyConfig policyConfig = new PolicyConfig( 15, 20, 25, parkingPolicy);
        String plateNumber = "FR 567 EU";
        ParkingSlot parkingSlot = new ParkingSlot(ParkingSlotType.EltCar50kw, false);
        parkingSlot.setId(25);
        Optional<ParkingSlot> optionalParkingSlot = Optional.of(parkingSlot);
        ParkingBill parkingBill =
                new ParkingBill(plateNumber, parkingSlot, LocalDateTime.of(2016, 7, 1, 12, 0));
        parkingBill.setId(35);
        parkingBill.setEnd(LocalDateTime.of(2016, 7, 1, 13, 00));
        parkingBill.setPrice(40.0);
        Optional<ParkingBill> optionalParkingBill = Optional.of(parkingBill);

        // initialize
        mvc.perform(
                MockMvcRequestBuilders.post("/initialize")
                        .content(toJson(policyConfig))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfStandardParkingSlot", is(15)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElectricCar20KWParkingSlot", is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfElectricCar50KWParkingSlot", is(25)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parkingPolicy.fixedAmt", is(15.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parkingPolicy.hourPrice", is(25.0)));

        // update pricing policy
        mvc.perform(
                MockMvcRequestBuilders.put("/pricingpolicy")
                        .content(toJson(updatedParkingPolicy))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fixedAmount", is(12.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hourPrice", is(17.0)));

        // entry parking
        mvc.perform(
                MockMvcRequestBuilders.get("/entryparking/FR 567 EU/EltCar20kw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.free", is(false)));

        // leave parking
        mvc.perform(
                MockMvcRequestBuilders.get("/exitparking/FR 567 EU")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.plateNumber", is("FR 567 EU")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", is(40.0)));
    }

    private byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
