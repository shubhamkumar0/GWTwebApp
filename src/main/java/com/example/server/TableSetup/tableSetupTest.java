package com.example.server.TableSetup;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class tableSetupTest {

    private final TableSetup tableSetup = new TableSetup();
    @BeforeAll
    public static void init() throws FileNotFoundException, SQLException, ClassNotFoundException {
        System.out.println("Testing Tables");
    }

    @Test
    public void TableInitialized() throws SQLException, ClassNotFoundException {
        tableSetup.initBookDetailsTable();
        boolean ans = tableSetup.areTablesAlreadyInitialized();
        assertTrue(ans == true,"passed!!!");
    }



}
