package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.CommandeRepository;
import com.bezkoder.springjwt.repository.FournisseurRepository;
import com.bezkoder.springjwt.repository.ResoucesRepository;
import com.bezkoder.springjwt.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StockServiceTest {

    @MockBean
    private StockRepository stockRepository;

    @MockBean
    private CommandeRepository commandeRepository;
    @MockBean
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private StockService stockService;

    @Test
    void testDetermineQuantityToAddForFurnitureCategory() {
        Resources resource = Resources.builder()
                .name("chair")
                .categorie(ResourcesCategorie.FURNITURE)
                .build();
        Stock stock = Stock.builder()
                .resource(resource)
                .categorieStock(ResourcesCategorie.FURNITURE)
                .build();

        int quantityToAdd = stockService.determineQuantityToAdd(stock);

        assertEquals(30, quantityToAdd); // Ensure correct value returned for furniture category
    }

    @Test
    void testCheckStockAndGenerateCommands() {
        Stock stock = Stock.builder()
                .stockID(1L)
                .location("Tunis")
                .replenishmentAlert(true)
                .entryDate(new Date())
                .purchaseDate(new Date())
                .expirationDate(new Date())
                .quality("good")
                .pourcentageDefauts(2)
                .categorieStock(ResourcesCategorie.ELECTRONICS)
                .build();

        int quantity = 20;
        Long fournisseurId = 1L;

        // Mock service method to generate commande
        when(commandeRepository.save(Mockito.any(Commande.class))).thenReturn(new Commande());

        Commande commande = stockService.createCommandeFromStock(stock, quantity, fournisseurId);

        assertNotNull(commande); // Ensure the commande object is created
        assertEquals(quantity, commande.getQuantity()); // Ensure quantity is as expected
        assertEquals("Tunis", commande.getLocation());
        assertEquals(true, commande.getReplenishmentAlert());
        assertEquals(stock.getEntryDate(), commande.getEntryDate());
        assertEquals(stock.getPurchaseDate(), commande.getPurchaseDate());
        assertEquals(stock.getExpirationDate(), commande.getExpirationDate());
        assertEquals(stock.getQuality(), commande.getQuality());
        assertEquals(stock.getPourcentageDefauts(), commande.getPourcentageDefauts());
        assertEquals(stock.getCategorieStock(), commande.getCategorieStock());
        assertEquals(fournisseurId, commande.getFournissID());
        assertEquals(StatusCommande.PENDING, commande.getStatusCommande());
    }

    @Test
    void testCheckStockAndSetReplenishmentAlert() {

        Fournisseur fournisseur = Fournisseur.builder()
                .fournisseurID(1L)
                .build();
        when(fournisseurRepository.findFournisseurByTypeFournisseurOrderByScoreDesc(ResourcesCategorie.ELECTRONICS))
                .thenReturn(List.of(fournisseur)); // Mock the return of a valid list of fournisseurs
        
        Long selectedFournisseurId = stockService.selectSupplierForCategory(ResourcesCategorie.ELECTRONICS);

        assertNotNull(selectedFournisseurId); // Ensure the selected supplier ID is not null
        assertEquals(1L, selectedFournisseurId); // Ensure it returns the correct supplier ID
    }

    @Test
    void testGenerateCommandsForLowStocksWithReplenishmentAlert() {

        Stock lowStock = Stock.builder()
                .stockID(1L)
                .categorieStock(ResourcesCategorie.ELECTRONICS)
                .replenishmentAlert(true)
                .build();

        Fournisseur fournisseur = Fournisseur.builder()
                .fournisseurID(1L)
                .build();

        // Mock repositories to return non-null lists
        when(stockRepository.findStockByReplenishmentAlert()).thenReturn(List.of(lowStock)); // Mock low stock retrieval
        when(fournisseurRepository.findFournisseurByTypeFournisseurOrderByScoreDesc(ResourcesCategorie.ELECTRONICS))
                .thenReturn(List.of(fournisseur)); // Mock fournisseur list

        stockService.generateCommandsForLowStocksWithReplenishmentAlert();

        // Verify methods are called with expected arguments
        verify(stockRepository).save(Mockito.any(Stock.class));
        verify(commandeRepository).save(Mockito.any(Commande.class));
    }
}
