package ru.psv.mj.init;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import com.jyloo.syntheticafx.BooleanColumnFilter;
import com.jyloo.syntheticafx.DateColumnFilter;
import com.jyloo.syntheticafx.FilterResourceLookup;
import com.jyloo.syntheticafx.PatternColumnFilter;
import com.jyloo.syntheticafx.RootPane;
import com.jyloo.syntheticafx.SimpleColumnFilter;
import com.jyloo.syntheticafx.SyntheticaFX;
import com.jyloo.syntheticafx.TextFormatterFactory;
import com.jyloo.syntheticafx.TitledBorderPane;
import com.jyloo.syntheticafx.XTableColumn;
import com.jyloo.syntheticafx.XTableView;
import com.jyloo.syntheticafx.filter.SimpleFilterModel;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class XTableViewDemo extends Application {
	
	private Parent createContent() {
		XTableView<?> table = createTableView();
		table.setTableMenuButtonVisible(true);
		
		BorderPane content = new BorderPane(table);
		content.setBottom(createOptionPane(table));
		return content;
	}

	private XTableView<?> createTableView() {
		ObservableList<Person> persons = createItems();
		// enable filter support
		FilteredList<Person> filtered = new FilteredList(persons);
		// data should be sortable too
		SortedList<Person> sorted = new SortedList(filtered);

		XTableView<Person> table = new XTableView<>(sorted);
		sorted.comparatorProperty().bind(table.comparatorProperty());
		table.setEditable(true);

		XTableColumn<Person, String> first = new XTableColumn<>("First Name");
		table.getColumns().addAll(first);
		first.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		// editing works just fine (because it's the cell content that's updated, not
		// the item)
		// however, to update the filter we need an extractor on the base list - see
		// #createItems()
		first.setCellFactory(TextFieldTableCell.forTableColumn());
		first.setColumnFilter(new PatternColumnFilter<>());

		XTableColumn last = new XTableColumn("LastName");
		table.getColumns().addAll(last);
		last.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		last.setColumnFilter(new PatternColumnFilter<>());

		XTableColumn<Person, String> emailHeader = new XTableColumn("Emails");
		table.getColumns().addAll(emailHeader);

		XTableColumn email = new XTableColumn("Primary");
		XTableColumn secondary = new XTableColumn("Secondary");
		emailHeader.getColumns().addAll(email, secondary);

		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		email.setColumnFilter(new PatternColumnFilter());
		secondary.setCellValueFactory(new PropertyValueFactory<>("secondaryMail"));
		secondary.setColumnFilter(new PatternColumnFilter());

		XTableColumn date = new XTableColumn("Lucky Day");
		// insert as third column
		// table.getColumns().add(2, date);
		table.getColumns().addAll(date);
		date.setCellValueFactory(new PropertyValueFactory<>("luckyDay"));
		date.setPrefWidth(200);
		DateColumnFilter dateFilter = new DateColumnFilter();
		date.setColumnFilter(dateFilter);

		XTableColumn<Person, Long> period = new XTableColumn<>("Days to Now");
		table.getColumns().addAll(period);
		period.setCellValueFactory(c -> {
			LocalDate lucky = c.getValue().getLuckyDay();
			return new SimpleLongProperty((int) ChronoUnit.DAYS.between(lucky, LocalDate.now())).asObject();
		});
		SimpleFilterModel<Person, Long> model = new SimpleFilterModel<>(
				(c, matchValue) -> c != null ? c < matchValue : false);
		period.setColumnFilter(new SimpleColumnFilter(model, TextFormatterFactory.LONG_TEXTFORMATTER_FACTORY));

		XTableColumn<Person, Boolean> bool = new XTableColumn<>("LastName > 5");
		table.getColumns().addAll(bool);
		bool.setCellValueFactory(c -> {
			String lastName = c.getValue().getLastName();
			return new SimpleBooleanProperty(lastName.length() > 5);
		});
		bool.setColumnFilter(new BooleanColumnFilter(FilterResourceLookup.YES_NO_CONVERTER));

		return table;
	}

	private ObservableList<Person> createItems() {
		// persons() is observable, wrapping needed for extractor
		ObservableList<Person> persons = FXCollections.observableList(Person.persons(),
				person -> new Observable[] { person.lastNameProperty(), person.firstNameProperty() });
		return persons;
	}

	private Pane createOptionPane(XTableView<?> table) {
		FlowPane pane = new FlowPane(10, 10);
		pane.setStyle("-fx-padding: 10 4");

		CheckBox filterVisible = new CheckBox("Show filter");
		filterVisible.selectedProperty().bindBidirectional(table.filterRowVisibleProperty());

		CheckBox menuButtonVisible = new CheckBox("Show menu button");
		menuButtonVisible.selectedProperty().bindBidirectional(table.tableMenuButtonVisibleProperty());

		CheckBox firstFilterable = new CheckBox("First column filterable");
		XTableColumn<Person, String> firstColumn = (XTableColumn<Person, String>) table.getColumns().get(0);
		firstFilterable.selectedProperty().bindBidirectional(firstColumn.filterableProperty());

		CheckBox includeHidden = new CheckBox("Include hidden columns");
		includeHidden.selectedProperty().bindBidirectional(table.getFilterController().includeHiddenProperty());

		CheckBox andFilters = new CheckBox("Use AND operation for multi-column filter");
		andFilters.selectedProperty().bindBidirectional(table.getFilterController().andFiltersProperty());

		pane.getChildren().addAll(filterVisible, menuButtonVisible, firstFilterable, includeHidden, andFilters);

		TitledBorderPane p = new TitledBorderPane("Options", pane);
		p.getStyleClass().add("top-border-only");
		p.setStyle("-fx-border-insets: 10 0 0 0");
		return p;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//SyntheticaFX.init("syntheticafx.theme.modena.SyntheticaFXStandard");
		SyntheticaFX.init("com.jyloo.syntheticafx.SyntheticaFXModena");
		primaryStage.setScene(new Scene(new RootPane(primaryStage, createContent()), 1000, 600));
		primaryStage.setTitle(getClass().getSimpleName());
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/****************************************************************************
	 * 
	 * Data Class
	 * 
	 * /
	 **************************************************************************/
	public static class Person {
		private final SimpleStringProperty firstName;
		private final SimpleStringProperty lastName;
		private final SimpleStringProperty email;
		private final SimpleStringProperty secondaryMail;
		private final SimpleObjectProperty<LocalDate> luckyDay;

		public Person(String fName, String lName, String email, LocalDate luckyDay) {
			this.firstName = new SimpleStringProperty(fName);
			this.lastName = new SimpleStringProperty(lName);
			this.email = new SimpleStringProperty(email);
			this.secondaryMail = new SimpleStringProperty("xx" + email);
			this.luckyDay = new SimpleObjectProperty<>(luckyDay);

		}

		public Person(String fName, String lName, String email) {
			this(fName, lName, email, null);
		}

		public String getFirstName() {
			return firstName.get();
		}

		public void setFirstName(String fName) {
			firstName.set(fName);
		}

		public StringProperty firstNameProperty() {
			return firstName;
		}

		public String getLastName() {
			return lastName.get();
		}

		public void setLastName(String fName) {
			lastName.set(fName);
		}

		public StringProperty lastNameProperty() {
			return lastName;
		}

		public String getEmail() {
			return email.get();
		}

		public void setEmail(String fName) {
			email.set(fName);
		}

		public StringProperty emailProperty() {
			return email;
		}

		public String getSecondaryMail() {
			return secondaryMailProperty().get();
		}

		public void setSecondaryMail(String mail) {
			secondaryMailProperty().set(mail);
		}

		public StringProperty secondaryMailProperty() {
			return secondaryMail;
		}

		public ObjectProperty<LocalDate> luckyDayProperty() {
			return luckyDay;
		}

		public LocalDate getLuckyDay() {
			return luckyDayProperty().get();
		}

		public void setLuckyDay(LocalDate luckyDay) {
			luckyDayProperty().set(luckyDay);
		}

		@Override
		public String toString() {
			return getLastName() + ", " + getFirstName();
		}

		public static ObservableList<Person> persons() {
			return FXCollections.observableArrayList(
					new Person("Jacob", "Smith", "jacob.smith@example.com", LocalDate.of(2016, Month.MAY, 22)),
					new Person("Isabella", "Johnson", "isabella.johnson@example.com",
							LocalDate.of(2000, Month.NOVEMBER, 10)),
					new Person("Ethan", "Williams", "ethan.williams@example.com", LocalDate.of(1996, Month.MAY, 2)),
					new Person("Emma", "Jones", "emma.jones@example.com", LocalDate.of(2016, Month.MAY, 23)),
					new Person("Lucinda", "Micheals", "lucinda.micheals@example.com",
							LocalDate.of(2015, Month.MAY, 17)),
					new Person("Michael", "Brown", "michael.brown@example.com", LocalDate.of(2014, Month.JANUARY, 8)),
					new Person("Barbara", "Pope", "barbara.pope@example.com", LocalDate.of(2016, Month.JULY, 3)),
					new Person("Penelope", "Rooster", "penelope.rooster@example.com",
							LocalDate.of(2016, Month.JUNE, 30)),
					new Person("Raphael", "Adamson", "raphael.adamson@example.com",
							LocalDate.of(2016, Month.APRIL, 24)));
		}
	}
}