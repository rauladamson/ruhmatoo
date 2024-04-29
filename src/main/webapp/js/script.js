/* MIAN PAGE */

function createNewFormChildDiv(HTMLText, name, inputType, placeholderText, className) {
    let formChildDiv = document.createElement("div"); // luuakse uus div container
    formChildDiv.classList.add("form-item"); // lisatakse klass
    formChildDiv.classList.add(className); // lisatakse klass

    let label = document.createElement("label"); // luuakse sisendvälja silt
    label.innerHTML = HTMLText;
    label.htmlFor = name;

    let input = document.createElement("input"); // luuakse sisendväli
    input.type = inputType;
    input.name = name;
    input.required = true;
    input.minlength = "4";
    input.size = "30";
    input.placeholder = placeholderText;

    let delButton = document.createElement('button');
    delButton.textContent = 'X';
    delButton.classList.add("delete-btn"); // lisatakse klass
    delButton.type = "button"
    delButton.onclick = function() { deleteEl(formChildDiv); };

    if (inputType === "url") {
        input.pattern="https://.*";
    }

    let div = document.getElementById("course-input-form-contaner");
    formChildDiv.appendChild(label);
    formChildDiv.appendChild(input);
    formChildDiv.appendChild(delButton);
    div.appendChild(formChildDiv);
}

function addTextInput() {
    let form = document.getElementById("course-input-form");
    createNewFormChildDiv("Lisa ainekood:",
        `text-input-${form.getElementsByClassName('textInput').length + 1}`,
        "text",
        "VALDKOND.00.0000",
        "textInput"

    );
}

function addUrlInput() {
    let form = document.getElementById("course-input-form-contaner");
    createNewFormChildDiv("Lisa url:",
        `url-input-${form.getElementsByClassName('urlInput').length + 1}`,
        "url",
        "https://ois2.ut.ee/#/courses/...",
        "urlInput");
}

function addCalInput() {
    let form = document.getElementById("course-input-form-contaner");
    createNewFormChildDiv("Lisa iCal link:",
        `cal-input-${form.getElementsByClassName('iCalInput').length + 1}`,
        "url",
        "...",
        "iCalInput");
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

function revealResult(){
    var el = document.getElementById("course-overview");
    el.classList.remove("hidden");

}

function showHiddenEl(elId) {
    let targetEl = document.getElementById(elId);

    let userInputGridEl = document.getElementById("userInput");

    userInputGridEl.classList.toggle("initial");
    // peidab/näitab elementi
    targetEl.classList.toggle("hidden");

}

  

  


  function submitForm(event) {
    event.preventDefault(); // Prevent the form from being submitted normally

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'inputServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onload = function() {
        if (this.status === 200) {
            console.log(this.responseText);

            var responseObj = JSON.parse(this.responseText); // JSON stringist objektiks

            let courseOverview = document.getElementById('course-overview');
            let childDiv1 = document.createElement("div"); // luuakse uus div container
            childDiv1.innerHTML = responseObj['url-input'];
            courseOverview.appendChild(childDiv1);

            let calendarGrid = document.getElementById('calendar-grid');
            let childDiv2 = document.createElement("div"); // luuakse uus div container
            childDiv2.innerHTML = responseObj['cal-input'];
            calendarGrid.appendChild(childDiv2);

        }
    };

    var formData = new FormData(event.target);
    xhr.send(new URLSearchParams(formData).toString());

  }

  /* KALENDER */
Date.prototype.getAdjustedDay = function() {
    var day = this.getDay();
    return day === 0 ? 6 : day - 1;
};

// kalendri kood
class Calendar {
    constructor() {

        this.selectedDayNr = new Date().getDate(); // algväärtuseks käesolev kuupäev
        this.selectedMonthNr = new Date().getMonth(); // algväärtuseks käesolev kuu
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

        this.selectedDayEl = document.getElementsByClassName('.day.selected');
        this.selectedMonthEl = document.getElementsByClassName('.monthA.selected');
        this.selectedYearEl = document.getElementsByClassName('.yearNr.selected');

        this.selectDay(this.selectedDayEl);
        this.selectMonth(this.selectedMonthEl);
        this.selectYear(this.selectedYearEl);


    }

    /* FUNKTSIOONID */

    createNewChidldEl(parent, i, outerTag, innerTag, outerClassListItems, innerClassListItems, text = null, nrToCheck = null, elToAddChildTo = null) {

        let outerEl = document.createElement(outerTag);
        let innerEl = null;

        outerEl.title = i;
        outerEl.dataset.value = i;
        outerEl.textContent = i;
        if (text) {outerEl.textContent = text;}

        outerClassListItems.forEach(item => outerEl.classList.add(item));

        if (innerTag !== null ) {
            innerEl = document.createElement(innerTag);
            if (innerTag === "a") {innerEl.href = "#";}

            innerClassListItems.forEach(item => innerEl.classList.add(item));
            outerEl.textContent = "";
            innerEl.textContent = i;

            outerEl.appendChild(innerEl);
        }

        if ((nrToCheck !== null) &&(i === nrToCheck)) {
            outerEl.classList.remove("hidden");
            outerEl.classList.remove("half-hidden");
            elToAddChildTo = outerEl;
        }

        parent.appendChild(outerEl);
        //if (returnBool && innerTag !== null) { return innerEl; }
    }

    updateMonth() { // vasakpoolses ribas kuu väärtuse muutmine
        var dateElement = document.querySelector('.date');
        dateElement.innerHTML = this.selectedMonthNr;
    }

    // muuda valitud kuud
    selectMonth(el) {
        /*const allMonths = document.getElementsByClassName("monthA");
        Array.from(allMonths).forEach((i) => {
            i.classList.remove("selected");
        });*/

        this.selectedMonthEl.classList.remove("selected");
        this.selectedMonthEl = el;

        var dateMonth = document.getElementById("dateMonth");
        dateMonth.innerText = el.innerText;
        el.classList.add("selected");

        /*var testP = document.getElementById('test');

        testP.innerText = "";
        testP.innerText += el;*/

        this.selectedMonthNr = el.dataset.value;
        this.addMonthDays();
    }

    selectDay(el) { // funktsioon muudab valitud päeva
        //const allDays = document.getElementsByClassName("dayA");

        this.selectedDayEl.classList.remove("selected");
        this.selectedDayEl = el;

        /*Array.from(allDays).forEach((i) => {

            if (i.dataset.value !== el.dataset.value)
                i.classList.remove("selected");
        });*/

        var dateDay = document.getElementById("dateDay");
        dateDay.innerText = el.title;
        el.classList.add("selected");
    }


    selectYear(el) { // funktsioon muudab valitud aastat
        const allYears = document.getElementsByClassName("yearNr");
        var selectedYearNr = Number(el.dataset.value);
        this.selectedYearEl = el;
        Array.from(allYears).forEach((i) => {
            var elYearNr = Number(i.dataset.value);

            i.classList.add("hidden");
            i.classList.remove("half-hidden");
            i.classList.remove("selected");

            if ((elYearNr === selectedYearNr - 1) || (elYearNr === selectedYearNr + 1)) {
                i.classList.remove("hidden");
                i.classList.add("half-hidden");
            }
        });

        el.classList.remove("hidden");
        el.classList.add("selected");

        //displayMonthData(selectedYearNr);
        this.addMonthDays();
    }

    // sündmuse kalendrisse lisamine
    addEvent(el) {
        el.classList.add("event");
    }

    // kõigi alamelementide kustutamine
    removeAllChildNodes(parent) {
        while (parent.firstChild) {
            parent.removeChild(parent.firstChild);
        }
    }

    addYears() {

        let fragment = document.createDocumentFragment();

        for (let i = this.currentYear - 5; i < this.currentYear + 5; i++) {

            this.createNewChidldEl(fragment, i, "h2", null, ["hidden", "yearNr"], null, "", this.currentYear, this.selectedYearEl);
        }
        this.yearSelect.appendChild(fragment);

    }


    addMonths() {

        let fragment = document.createDocumentFragment();

        // kuud lisatakse dünaamiliselt
        Object.keys(this.months).forEach((i) => {

            this.createNewChidldEl(fragment, i, "li", null, ["monthA"], null, this.months[i], this.selectedMonthNr, this.selectedMonthEl);


        });


        this.monthList.appendChild(fragment);
    }


// nädalapäaevad

    addWeekdays() {

        let fragment = document.createDocumentFragment();
        Object.keys(this.days).forEach((i) => {

            this.createNewChidldEl(fragment, i, "li", null, ["weekday"], null, this.days[i], null, null);

        });

        this.dayList.appendChild(fragment);

    }


// kuu päeavde lisamine
    addMonthDays() {

        this.removeAllChildNodes(this.daysList); // olemasolevad elemendid kustutatakse

        var totalElCount = 0;

        // päevade arv kuus
        var daysInMonth = new Date(this.selectedYear, this.selectedMonthNr + 1, 0).getDate();
        var weekdayOfFirst = new Date(this.selectedYear, this.selectedMonthNr).getAdjustedDay();
        var weekdayOfLast =  new Date(this.selectedYear, this.selectedMonthNr, daysInMonth).getAdjustedDay();

        let fragment = document.createDocumentFragment();

        // eelmise kuu päevade lisamine, kui kuu ei alga esmaspäevaga
        if (weekdayOfFirst !== 0) {

            let daysInPrevMonth = new Date(this.selectedYear, this.selectedMonthNr - 1, 0).getDate();
            if (this.selectedMonthNr === 1) {
                daysInPrevMonth = new Date(this.selectedYear - 1, this.selectedMonthNr - 1, 0).getDate();
            }

            for (let i = daysInPrevMonth - weekdayOfFirst; i < daysInPrevMonth; i++) {
                this.createNewChidldEl(fragment, i, "div", "a", ["day", "half-hidden"], ["dayA"]);

                totalElCount += 1;
            }
        }

        // kuu päevade lisamine
        for (let i = 1; i <= daysInMonth; i++) {

            this.createNewChidldEl(fragment, i, "div", "a", ["day", "half-hidden"], ["dayA"], "", this.selectedDayNr, this.selectedDayEl);


            totalElCount += 1;
        }

        // järgmise kuu päevade lisamine, kui kuu ei lõpe pühapäevaga
        if (weekdayOfLast !== 6) {

            for (let i = 1; i <= 35 - totalElCount; i++) {
                this.createNewChidldEl(fragment, i, "div", "a", ["day", "half-hidden"], ["dayA"]);
            }
        }

        this.daysList.appendChild(fragment);
    }
}

const calendar = new Calendar(); // uue Calendar klassi objekti loomine
