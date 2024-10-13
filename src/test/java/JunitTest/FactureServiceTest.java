package JunitTest;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

public class FactureServiceTest {

    @Mock
    private ClientRep clientRep;

    @Mock
    private ContartRep contartRep;

    @Mock
    private FactureRep factureRep;

    @InjectMocks
    private ServiceClient factureService;

    private Client mockClient;
    private Contract mockContract;
    private Facture mockFacture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);


        mockClient = new Client();
        mockClient.setIdClient(1L);

        mockContract = new Contract();
        mockContract.setIdContract(1L);
        mockContract.setFactures(new ArrayList<>());

        mockFacture = new Facture();
        mockFacture.setIdFacture(1L);
    }

    @Test
    public void testAjouterFacture() {
        Long idclient = 1L;
        Long idcontart = 1L;


        when(clientRep.findById(idclient)).thenReturn(Optional.of(mockClient));
        when(contartRep.findById(idcontart)).thenReturn(Optional.of(mockContract));
        when(factureRep.save(any(Facture.class))).thenReturn(mockFacture);


        Long returnedId = factureService.ajouterFacture(mockFacture, idclient, idcontart);


        assertEquals(mockFacture.getIdFacture(), returnedId);
        assertEquals(mockFacture.getClient(), mockClient);
        assertEquals(mockFacture.getContract(), mockContract);
        assertEquals(mockFacture.getPaid_amount(), BigDecimal.valueOf(0.0));
        assertEquals(mockFacture.getPayment_status(), "Outstanding");


        verify(contartRep).save(mockContract);
        verify(factureRep).save(mockFacture);
    }





}
