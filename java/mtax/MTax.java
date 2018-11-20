import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class MTax implements Constant {
    
    private final static Logger LOGGER = Logger.getLogger(MTax.class.getName());

    public MTax(){
        
    }
    
	public static List<String> validate(List<XTax> xTaxList) {
		List<String> errorList = new ArrayList<>();
		List<String> validIds = new ArrayList<>();
		int counter = 0;
		try {
			if (xTaxList != null && !xTaxList.isEmpty()) {
				for (XTax tax : xTaxList) {
					if (tax.getId() != null) {
						validIds.add(tax.getId().toString());
					}
	
					if (tax.getTax() == null) {
						errorList.add(Constantes.VALIDATE_MSG_TAX);
					}
	
					if (!tax.isLocal()) {
						counter++;
					}
				}
				if (counter <= 0) {
					errorList.add(Constantes.VALIDATE_MSG_TASA_LOCAL);
				}
				if(!xTaxList.isEmpty()){
					errorList = validaIds(validIds, xTaxList, errorList);
				}
			}

		} catch (Exception e) {
			LOGGER.severe("Ocurrio un error en methodo MTax.validate: " + e.getMessage());
			throw e;
		}
		return errorList;
	}

	public static List<String> validaIds(List<String> validIds, List<XTax> xTaxList, List<String> errorList) {
		List<XTax> xt = new ArrayList<XTax>();
		HashMap<String, XTax> mapTax = new HashMap<String, XTax>();
		try {
			xt = TaxsByListId(validIds, false);
			if (xt.size() != validIds.size()) {
				errorList.add(Constantes.VALIDATE_MSG_TAX_IDS);
			} else {
				for (XTax tax : xt) {
					mapTax.put(tax.getId().toString(), tax);
				}
				for (int i = 0; i < xTaxList.size(); i++) {
					if (mapTax.containsKey(xTaxList.get(i).getId().toString())) {
						xTaxList.get(i).setCreated(mapTax.get(xTaxList.get(i).getId().toString()).getCreated());
					}
				}
			}
		} catch (Exception e) {
			LOGGER.severe("Ocurrio un error en methodo MTax.validaIds: " + e.getMessage());
			throw e;
		}
		return errorList;
	}
}
