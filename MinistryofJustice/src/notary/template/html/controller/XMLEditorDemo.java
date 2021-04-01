package notary.template.html.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import com.mulya.PetrovichDeclinationMaker;
import com.mulya.enums.Case;
import com.mulya.enums.Gender;
import com.mulya.enums.NamePart;

public class XMLEditorDemo {

	public static void main(String[] args) throws IOException, URISyntaxException {
		PetrovichDeclinationMaker maker = PetrovichDeclinationMaker.getInstance("/src/com.mulya/rules.json");

		maker.make(NamePart.FIRSTNAME, Gender.MALE, Case.GENITIVE, "Иван"); // Ивана
		maker.make(NamePart.LASTNAME, Gender.MALE, Case.INSTRUMENTAL, "Иванов"); // Ивановым
		maker.make(NamePart.MIDDLENAME, Gender.FEMALE, Case.DATIVE, "Ивановна"); // Ивановне
	}
}