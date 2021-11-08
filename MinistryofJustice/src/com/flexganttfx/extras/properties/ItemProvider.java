package com.flexganttfx.extras.properties;

import java.util.List;
import org.controlsfx.control.PropertySheet;

public interface ItemProvider<T> {
  List<PropertySheet.Item> getPropertySheetItems(T paramT);
}


/* Location:              C:\Users\Said.000\git\ZagsNotary\MinistryofJustice\lib\flexganttfx-extras-8.12.0.jar!\com\flexganttfx\extras\properties\ItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */