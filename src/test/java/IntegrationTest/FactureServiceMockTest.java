package IntegrationTest;



        import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;
        import com.bezkoder.springjwt.Service.ServiceClient;
        import com.bezkoder.springjwt.models.Client;
        import com.bezkoder.springjwt.models.Contract;
        import com.bezkoder.springjwt.models.Facture;
        import com.bezkoder.springjwt.repository.ClientRep;
        import com.bezkoder.springjwt.repository.ContartRep;
        import com.bezkoder.springjwt.repository.FactureRep;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.InjectMocks;
        import org.mockito.junit.jupiter.MockitoExtension;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import java.math.BigDecimal;
        import java.util.ArrayList;
        import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class FactureServiceMockTest {

    @Autowired
    private ClientRep clientRep;

    @MockBean
    private ContartRep contartRep;

    @MockBean
    private FactureRep factureRep;

    @InjectMocks
    private ServiceClient serviceClient; // This should be wired correctly now

    private Client mockClient;
    private Contract mockContract;
    private Facture mockFacture;

    @BeforeEach
    public void setup() {
        mockClient = new Client();
        mockClient.setIdClient(1L);

        mockContract = new Contract();
        mockContract.setIdContract(1L);
        mockContract.setFactures(new ArrayList<>());

        mockFacture = new Facture();
        mockFacture.setIdFacture(1L);
    }

    @Test
    public void testAjouterFacture_Integration() {
        Long idclient = 1L;
        Long idcontart = 1L;

        // Setup mocks
        when(clientRep.findById(idclient)).thenReturn(Optional.of(mockClient));
        when(contartRep.findById(idcontart)).thenReturn(Optional.of(mockContract));
        when(factureRep.save(any(Facture.class))).thenReturn(mockFacture);

        // Call the method
        Long returnedId = serviceClient.ajouterFacture(mockFacture, idclient, idcontart);

        // Assertions
        assertEquals(mockFacture.getIdFacture(), returnedId);
        assertEquals(mockFacture.getClient(), mockClient);
        assertEquals(mockFacture.getContract(), mockContract);
        assertEquals(mockFacture.getPaid_amount(), BigDecimal.valueOf(0.0));
        assertEquals(mockFacture.getPayment_status(), "Outstanding");

        // Verify interactions
        verify(contartRep).save(mockContract);
        verify(factureRep).save(mockFacture);
    }
}