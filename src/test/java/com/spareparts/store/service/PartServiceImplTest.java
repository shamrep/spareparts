package com.spareparts.store.service;


import com.spareparts.store.model.Part;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartServiceImplTest {

    @Test
    void partsEmptyIfNoPartsAdded () {
//        fail();
        PartServiceImpl partService = new PartServiceImpl();
        var allParts = partService.getAllParts();
        assertTrue(allParts.isEmpty());
    }

}
