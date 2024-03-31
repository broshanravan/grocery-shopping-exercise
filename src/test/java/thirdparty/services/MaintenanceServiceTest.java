package thirdparty.services;

import Services.MaintenanceService;
import Services.MaintenanceServiceImpl;
import org.junit.jupiter.api.Test;

public class MaintenanceServiceTest {

    MaintenanceService maintenanceService = new MaintenanceServiceImpl();
    @Test
    public void barCodeFormatTest(){
        String invalidContentsBarCode = "123w6";
        String validContentsBarCode = "MN123";
        assert(!maintenanceService.isBarFormatCodeValid(invalidContentsBarCode));
        assert(maintenanceService.isBarFormatCodeValid(validContentsBarCode));

    }
    @Test
    public void barCodeLengthTest() {
        String longerLengthBarCode = "BN1234";
        String shorterLengthBarCode = "BN1234";
        String validContentsBarCode = "MN123";
        assert(!maintenanceService.isBarFormatCodeValid(longerLengthBarCode));
        assert(!maintenanceService.isBarFormatCodeValid(shorterLengthBarCode));
        assert(maintenanceService.isBarFormatCodeValid(validContentsBarCode));

    }
    @Test
    public void barCharactersTest() {

        String containsSpecialCharacter = "£r456";
        String doseNotContainSpecialCharacter = "br456";
       assert (maintenanceService.containsSpecialCharacters(containsSpecialCharacter));
       assert(!maintenanceService.containsSpecialCharacters(doseNotContainSpecialCharacter));

    }

}
