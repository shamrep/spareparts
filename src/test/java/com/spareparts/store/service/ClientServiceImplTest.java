package com.spareparts.store.service;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImplTest {

    @Test
    void partsEmptyIfNoPartsAdded () {
//        fail();
        ClientServiceImpl partService = new ClientServiceImpl();
        var allParts = partService.getAllParts();
        assertTrue(allParts.isEmpty());
    }

}
