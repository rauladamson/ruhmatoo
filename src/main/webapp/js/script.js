function createNewFormChildDiv(HTMLText, name, inputType, placeholderText) {
    var formChildDiv = document.createElement("div"); // luuakse uus div container
        formChildDiv.classList.add("form-item"); // lisatakse klass
    
    var label = document.createElement("label"); // luuakse sisendvälja silt
              label.innerHTML = HTMLText;
              label.htmlFor = name;
    
    var input = document.createElement("input"); // luuakse sisendväli
              input.type = inputType;
              input.name = name;
              input.required = true;
              input.minlength = "4";
              input.size = "30";
              input.placeholder = placeholderText;
    
    var delButton = document.createElement('button');
      delButton.textContent = 'X';
      delButton.classList.add("delete-btn"); // lisatakse klass
      delButton.onclick = function() {
      deleteEl(formChildDiv);
  };
    
    if (inputType == "url") {
              input.pattern="https://.*";
    }
    
    var div = document.getElementById("course-input-form-contaner");
              formChildDiv.appendChild(label);
              formChildDiv.appendChild(input);
              formChildDiv.appendChild(delButton);
              div.appendChild(formChildDiv);
  }
  
  function addTextInput() {
    
    var form = document.getElementById("course-input-form");
    
    createNewFormChildDiv("Lisa ainekood:", // sisendvälja silt
                          `input-${form.getElementsByTagName('input').length + 1}`, // sisendvälja nimi
                          "text", // sisendvälja tüübi määramine
                          "VALDKOND.00.0000"
                         ); 
  }
  
  function addUrlInput() {
    
    var form = document.getElementById("course-input-form-contaner");
    
    createNewFormChildDiv("Lisa url:", // sisendvälja silt
                          `input-${form.getElementsByTagName('input').length + 1}`, // sisendvälja nimi
                          "url",// sisendvälja tüübi määramine
                          "https://ois2.ut.ee/#/courses/..."); 
  }
  
  function deleteAllUserInput() {
  
    var form = document.getElementById("course-input-form-contaner");
          form.innerHTML = "";
  }
  
  function deleteEl(directParentEl) {
    
    if (directParentEl) {
      directParentEl.parentNode.removeChild(directParentEl);
    } 
  }

  function submitForm(event) {
    event.preventDefault(); // Prevent the form from being submitted normally

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'inputServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function() {
        if (this.status == 200) {
            // Append the response text to the page
            var resultDiv = document.getElementById('result');
            resultDiv.innerHTML = this.responseText;
        }
    };

    var formData = new FormData(event.target);
    xhr.send(new URLSearchParams(formData).toString());

  }

// kalendri kood

Date.prototype.getAdjustedDay = function() {
    var day = this.getDay();
    return day === 0 ? 6 : day - 1;
};

class Calendar {
    constructor() {
        this.selectedDayNr = 1; // algväärtuseks 1
        this.selectedMonthNr = 0; // algväärtuseks Jaanuar
        this.selectedYear = 2024; // algväärtuseks 2024
        this.currentYear = new Date().getFullYear(); // praegune aasta
        this.days = {0: "E", 1: "T", 2: "K", 3: "N", 4: "R", 5: "L", 6: "P"};
        this.months = {0: 'Jan', 1: 'Feb', 2: 'Mar', 3: 'Apr', 4: 'May', 5: 'Jun', 6: 'Jul', 7: 'Aug', 8: 'Sep', 9: 'Oct', 10: 'Nov', 11: 'Dec'};
        this.dayList = document.querySelector(".weekday"); // nädalapäevad
        this.daysList = document.querySelector(".days"); // kuupäeavd
        this.yearSelect = document.querySelector(".yearSelect"); // aastad
        this.daysList.className = "days";
        this.monthList = document.getElementById("monthList");
        this.monthList.addEventListener('change', () => this.updateMonth());

        this.SelectedDayEl = null;
        this.SelectedMonthEl = null;
        this.SelectedYearhEl = null;

        // Add event listener to the parent element
        this.yearSelect.addEventListener('click', (event) => {
            // Check if the clicked element is a yearNr
            if (event.target.classList.contains('yearNr')) {
                this.selectYear(event.target);
            }
        });

        this.monthList.addEventListener('click', (event) => {
            // Check if the clicked element is a monthA
            if (event.target.classList.contains('monthA')) {
                this.selectMonth(event.target);
            }
        });

        this.daysList.addEventListener('click', (event) => {
            // Check if the clicked element is a dayA
            if (event.target.classList.contains('dayA')) {
                this.selectDay(event.target);
            }
        });

        // funktsioonide väljakutsed initsialiseerimisel
        this.addYears(); // aastate lisamine (2024 +- 5)
        this.addWeekdays(); // nädalapäevade lisamine (E - P)
        this.addMonths(); // kuude lisamine (Jan - Dec)
        this.addMonthDays(); // kuu päevade lisamine (1 - 28/29/30/31)

        //if (i == 1) {this.selectDay(a);}
        //if (i == 8 || i == 10 || i == 27) {this.addEvent(a);}
       // if (i == 0) {this.selectMonth(a); } // jaanuar valitakse vaikimisi

    }

    /* FUNKTSIOONID */

    updateMonth() { // vasakpoolses ribas kuu väärtuse muutmine
        var dateElement = document.querySelector('.date');
        dateElement.innerHTML = this.selectedMonthNr;
    }

    // muuda valitud kuud
    selectMonth(el) {

        // eelmiselt valitud elemendilt eemaldatakse "selected" klass
        if (this.selectedMonthEl) {this.selectedMonthEl.classList.remove("selected");}
        
        this.selectedMonthEl = el;
        el.classList.add("selected");
        this.selectedMonthNr = el.dataset.value;
        
        var dateMonth = document.getElementById("dateMonth");
        dateMonth.textContent = el.title;
        
        this.addMonthDays();
    }

    selectDay(el) { // funktsioon muudab valitud päeva
        // eelmiselt valitud elemendilt eemaldatakse "selected" klass
        if (this.selectedDayEl) {this.selectedDayEl.classList.remove("selected");}

        this.selectedDayEl = el;
        el.classList.add("selected");
        this.selectedMonthNr = el.dataset.value;
        
        var dateDay = document.getElementById("dateDay");
        dateDay.textContent = el.title;
    }


    selectYear(el) { // funktsioon muudab valitud aastat

        // eelmiselt valitud elemendilt eemaldatakse "selected" klass
        if (this.selectedYearEl) {
            this.selectedYearEl.classList.add("hidden");
            this.selectedYearEl.classList.remove("selected");
        }

        this.selectedYearEl = el;
        el.classList.add("selected");
        this.selectedYear = el.dataset.value;

        // eelmise ja järgmise elemendi läbipaistvuseks on 50%
        let prevElement = element.previousElementSibling;
        let nextElement = element.nextElementSibling;

        prevElement.classList.add("half-hidden");
        nextElement.classList.add("half-hidden");
        prevElement.classList.remove("hidden");
        nextElement.classList.remove("hidden");
        /*const allYears = document.getElementsByClassName("yearNr");
        let selectedYearNr = Number(el.dataset.value);

        Array.from(allYears).forEach((i) => {
            let elYearNr = Number(i.dataset.value);

            i.classList.add("hidden");
            i.classList.remove("half-hidden");
            i.classList.remove("selected");

            if ((elYearNr == selectedYearNr - 1) || (elYearNr == selectedYearNr + 1)) {
                i.classList.remove("hidden");
                i.classList.add("half-hidden");
            }
        });*/

        el.classList.remove("hidden");
        el.classList.add("selected");

        this.addMonthDays();
    }
    

    // sündmuse kalendrisse lisamine
    addEvent(el) {el.classList.add("event");}

    // kõigi alamelementide kustutamine
    removeAllChildNodes(parent) {
        while (parent.firstChild) {
            parent.removeChild(parent.firstChild);
        }
    }

     addYears() {
        for (let i = this.currentYear - 5; i < this.currentYear + 5; i++) {

//            createNewChidldEl(this.monthList, i, "li", "a", ("monthA"), text=this.months[i]);

            let year = document.createElement("li"); // create a new li
            let yearNr = document.createElement("h2"); // create a new a

            yearNr.textContent = i;
            yearNr.dataset.value = i;

            if (i != this.currentYear) { yearNr.classList.add("hidden"); }
            else { yearNr.classList.add("selected"); }

            yearNr.classList.add("yearNr");
            year.appendChild(yearNr);
            this.yearSelect.appendChild(year);
        }
    }


    addMonths() {
        // kuud lisatakse dünaamiliselt
        Object.keys(this.months).forEach((i) => {
            createNewChidldEl(this.monthList, i, "li", "a", ("monthA"), text=this.months[i]);
        });
    }

    createNewChidldEl(parent, i, outerTag, innerTag, classListItems, text = null, dataDict = null) {
        let outerEl = document.createElement(outerTag);
        let innerEl = document.createElement(innerTag);

        if (innerTag == "a") {innerEl.href = "#";}

        innerEl.title = i;
        innerEl.dataset.value = i;
        innerEl.textContent = i;

        innerEl.classList.add(classListItems);

        outerEl.appendChild(innerEl);
        parent.appendChild(outerEl);
    }

    // nädalapäaevad
    addWeekdays() {
        Object.keys(this.days).forEach((i) => {
            createNewChidldEl(this.dayList, i, "li", "a", (), text=this.days[i]);
        });
    }


    // kuu päeavde lisamine
    addMonthDays() {

        this.removeAllChildNodes(this.daysList); // olemasolevad elemendid kustutatakse

        let totalElCount = 0;

        // päevade arv kuus
        let daysInMonth = new Date(this.selectedYear, this.selectedMonthNr + 1);
        let weekdayOfFirst = new Date(this.selectedYear, this.selectedMonthNr).getAdjustedDay();
        let weekdayOfLast =  new Date(this.selectedYear, this.selectedMonthNr, daysInMonth).getAdjustedDay();

        let testP = document.getElementById('test');

        testP.textContent = "";
        testP.textContent = daysInMonth;
        testP.textContent += " " + weekdayOfFirst;
        testP.textContent += " " + this.selectedYear;
        testP.textContent += " " + this.selectedMonthNr;
        testP.textContent += " " + weekdayOfLast;

        // eelmise kuu päevade lisamine, kui kuu ei alga esmaspäevaga
        if (weekdayOfFirst != 0) {

            let daysInPrevMonth = new Date(this.selectedYear, this.selectedMonthNr - 1, 0).getDate();
            if (this.selectedMonthNr == 1) {
                daysInPrevMonth = new Date(this.selectedYear - 1, this.selectedMonthNr - 1, 0).getDate();
            }

            for (let i = daysInPrevMonth - weekdayOfFirst; i < daysInPrevMonth; i++) {
                createNewChidldEl(this.daysList, i, "li", "a", ("dayA", "half-hidden"));
                totalElCount += 1;
            }
        }

        // kuu päevade lisamine
        for (let i = 1; i <= daysInMonth; i++) {
            createNewChidldEl(this.daysList, i, "li", "a", ("dayA", "half-hidden"));
            totalElCount += 1;
        }

        // järgmise kuu päevade lisamine, kui kuu ei lõpe pühapäevaga
        if (weekdayOfLast != 6) {

            for (let i = 1; i <= 35 - totalElCount; i++) {
                createNewChidldEl(this.daysList, i, "li", "a", ("dayA", "half-hidden"));
            }
        }
    }
}
const calendar = new Calendar(); // uue Calendar klassi objekti loomine



