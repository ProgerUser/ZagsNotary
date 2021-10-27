function generate_table(cell_, row_) {
	var sel, range;
	if (window.getSelection && (sel = window.getSelection()).rangeCount) {
		range = sel.getRangeAt(0);
		range.collapse(true);
		//----------------------------------
		// get the reference for the body
		var body = document.getElementsByTagName("body")[0];

		// creates a <table> element and a <tbody> element
		var tbl = document.createElement("table");
		var tblBody = document.createElement("tbody");

		// creating all cells
		for (var i = 0; i < cell_; i++) {
			// creates a table row
			var row = document.createElement("tr");

			for (var j = 0; j < row_; j++) {
				// Create a <td> element and a text node, make the text
				// node the contents of the <td>, and put the <td> at
				// the end of the table row
				var cell = document.createElement("td");
				//var cellText = document.createTextNode("cell in row " +   + ", column " + j);
				var cellText = document.createTextNode("cell in row " + + ", column " + j);
				cell.appendChild(cellText);
				row.appendChild(cell);
			}

			// add the row to the end of the table body
			tblBody.appendChild(row);
		}

		// put the <tbody> in the <table>
		tbl.appendChild(tblBody);
		// appends <table> into <body>
		body.appendChild(tbl);
		// sets the border attribute of tbl to 2;
		tbl.setAttribute("border", "1");

		//----------------------------------

		range.insertNode(tbl);
		// Move the caret immediately after the inserted span
		range.setStartAfter(tbl);
		range.collapse(true);
		sel.removeAllRanges();
		sel.addRange(range);
	}
}

generate_table($cell$, $row$);