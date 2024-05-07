/* KALENDER */
Date.prototype.getAdjustedDay = function() {
    var day = this.getDay();
    return day === 0 ? 6 : day - 1;
};


/* MAIN PAGE */

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

    //console.log(elId);
    let targetEl = document.getElementById(elId);
    //console.log(targetEl);
    //let userInputGridEl = document.getElementById("userInput");

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

            var responseObj = JSON.parse(this.responseText); // JSON stringist objektiks

            //console.log(responseObj);
            let courseOverview = document.getElementById('course-overview');
            let childDiv1 = document.createElement("div"); // luuakse uus div container
            childDiv1.innerHTML = responseObj['url-input'];
            courseOverview.appendChild(childDiv1);

            let calendarGrid = document.getElementById('calendar-grid');

            let calInput = JSON.parse(responseObj['cal-input']);


            let newiCalObj = new iCalObj(calInput['iCalLink'], calInput['events']);
            let newiCalObjEvents = newiCalObj.getEvents();

            const onetimeEvents = document.getElementById('onetime-events').getElementsByClassName('event-dropdown')[0];
            const recurringEvents = document.getElementById('recurring-events').getElementsByClassName('event-dropdown')[0];

            console.log(onetimeEvents);
            console.log(recurringEvents);

            for (let event of newiCalObjEvents) {

                let childDiv2 = document.createElement("div"); // luuakse uus div container
                childDiv2.innerHTML = event;

                //console.log(event.isRecurring())

                if (event.isRecurring()) {
                    console.log("recurring");
                    recurringEvents.appendChild(childDiv2);
                } else {
                    console.log("onetime");
                    onetimeEvents.appendChild(childDiv2);
                }

                calendarGrid.appendChild(childDiv2);
            }

            console.log(onetimeEvents);
            console.log(recurringEvents);
        }
    };

    var formData = new FormData(event.target);
    xhr.send(new URLSearchParams(formData).toString());

}

class iCalObj {
    constructor(iCalLink, eventsInput) {
        this.iCalLink = null;
        this.events = [];

        for (let event of eventsInput) {
            //console.log(event);
            this.events.push(new CalendarEvent(event.uid, event.summary, event.location, event.description, event.categories, new Date(event.start), new Date(event.end), event.duration, event.occurrences, event.recurring));
        }

        //console.log(this.events);
    }
    getEvents() {
        return this.events;
    }
}

class CalendarEvent {
    constructor(uid, summary, location, description, categories, start, end, duration, occurrences, recurring) {
        this.uid = uid;
        this.summary = summary;
        this.location = location;
        this.description = description;
        this.categories = categories;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.occurrences = occurrences;
        this.recurring = recurring;
    }
    getSummary() {
        return this.summary;
    }
    setEnd(end) {
        this.end = this.end;
    }
    getStart() {
        return this.start;
    }
    getEnd() {
        return this.end;
    }
    getOccurrences() {
        return this.occurrences;
    }
    addOccurrence(occurrence) {
        this.occurrences.add(occurrence);
    }

    isRecurring() {
        return this.recurring;
    }
}



// kalendri kood
class Calendar {
    constructor() {
        this.currentDate = new Date(); // algväärtuseks käesolev kuupäev
        this.selectedDate = new Date(); // algväärtuseks käesolev kuupäev

        this.days = {0: "E", 1: "T", 2: "K", 3: "N", 4: "R", 5: "L", 6: "P"};

        this.months = {
            0: { 'name': 'Jaanuar', 'days': 31 },
            1: { 'name': 'Veebruar', 'days': 28 }, // 29 liigaasta korral
            2: { 'name': 'Märts', 'days': 31 },
            3: { 'name': 'Aprill', 'days': 30 },
            4: { 'name': 'Mai', 'days': 31 },
            5: { 'name': 'Juuni', 'days': 30 },
            6: { 'name': 'Juuli', 'days': 31 },
            7: { 'name': 'August', 'days': 31 },
            8: { 'name': 'September', 'days': 30 },
            9: { 'name': 'Oktoober', 'days': 31 },
            10: { 'name': 'November', 'days': 30 },
            11: { 'name': 'Detsember', 'days': 31 }
        };

        this.dayList = document.querySelector(".weekday"); // nädalapäevad
        this.daysList = document.querySelector(".days"); // kuupäeavd
        this.yearSelect = document.querySelector(".yearSelect"); // aastad
        this.daysList.className = "days";
        this.monthList = document.getElementById("monthList");

        // Add event listener to the parent element
        this.yearSelect.addEventListener('click', (event) => { this.selectYear(event.target);});
        this.monthList.addEventListener('click', (event) => {this.selectMonth(event.target);});
        this.daysList.addEventListener('click', (event) => {this.selectDay(event.target);});

        // funktsioonide väljakutsed initsialiseerimisel
        this.addYears(); // aastate lisamine (2024 +- 5)
        this.addWeekdays(); // nädalapäevade lisamine (E - P)
        this.addMonths(); // kuude lisamine (Jan - Dec)
        this.addMonthDays(); // kuu päevade lisamine (1 - 28/29/30/31)

        //this.selectedDayEl = document.getElementsByClassName('.day.selected');
        this.selectedMonthEl = document.getElementsByClassName('month selected')[0];
        this.selectedYearEl = document.getElementsByClassName('yearNr selected')[0];

        //let testP = document.getElementById("test");
        // testP.innerText = "";
        // testP.innerText += " " + this.selectedDate;
        //testP.innerText += " " + this.selectedMonthEl;
        //testP.innerText += " " + this.selectedYearEl;

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



        if (nrToCheck !== null) {

            if (i === nrToCheck) {
                outerEl.classList.add("selected"); outerEl.classList.remove("hidden");
                outerEl.classList.remove("half-hidden");
                elToAddChildTo = outerEl;
            } else if ((i === nrToCheck - 1) || (i === nrToCheck + 1)) {
                outerEl.classList.remove("hidden");
            }

        }

        parent.appendChild(outerEl);

    }

    // muuda valitud kuud
    selectMonth(el) {

        let oldSelectedMonthEl = document.getElementsByClassName('month selected')[0];

        if (oldSelectedMonthEl) {oldSelectedMonthEl.classList.remove("selected");}


        /*let testP = document.getElementById("test");

          testP.innerText = "";
         testP.innerText += " " + this.selectedDate;
          testP.innerText += " " + el.dataset.value;*/
        //var dateMonth = document.getElementById("dateMonth");
        //dateMonth.innerText = el.innerText;

        //testP.innerText += " " + dateMonth.innerText;
        el.classList.add("selected");

        this.selectedMonthEl = el;

        this.selectedDate.setMonth(el.dataset.value);

        //      testP.innerText += " " + this.selectedDate;
//
        //this.selectedMonthNr = el.dataset.value;
        this.addMonthDays();
        //this.updateMonth();

        // kui konkreetset kuupäeva pole valitud, siis kuvatakse kõik kuu sündmused
        var dateElement = document.querySelector('.date');
        dateElement.innerText = el.innerText;
    }

    selectDay(el) { // funktsioon muudab valitud päeva

        /*let oldSelectedDayEl = document.getElementsByClassName('day selected')[0];
        oldSelectedDayEl.classList.remove("selected");*/

        //var dateDay = document.getElementById("dateDay");
        //dateDay.innerText = el.title;
        el.classList.add("selected");

        this.selectedDate.setDate(el.dataset.value);
    }


    selectYear(el) { // funktsioon muudab valitud aastat

        let selectedYearEl = document.getElementsByClassName('yearNr selected')[0];

        selectedYearEl.classList.remove("selected");
        selectedYearEl.classList.add("half-hidden");

        let prevSelectedYear = this.selectedDate.getFullYear();
        let selectedYearNr = Number(el.dataset.value);

        // leitakse peidetav element
        let elToHide = document.querySelectorAll('.yearNr[data-value="' + ((selectedYearNr > prevSelectedYear) ? selectedYearNr - 2: selectedYearNr + 2) + '"]')[0];

        if (elToHide) {
            elToHide.classList.remove("half-hidden");
            elToHide.classList.add("hidden");
        }

        // leitakse näidatav element
        let elToShow = document.querySelectorAll('.yearNr[data-value="' + ((selectedYearNr > prevSelectedYear) ? selectedYearNr + 1: selectedYearNr - 1) + '"]')[0];

        if (elToShow) {
            elToShow.classList.remove("hidden");
            elToShow.classList.add("half-hidden");
        }


        el.classList.remove("half-hidden");
        el.classList.add("selected");

        this.selectedDate.setFullYear(selectedYearNr);

        this.selectedYearEl = el;
        //this.selectedYear = selectedYearNr;
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
        for (let i = this.selectedDate.getFullYear() - 5; i < this.selectedDate.getFullYear() + 5; i++) { this.createNewChidldEl(fragment, i, "h2", null, ["hidden", "half-hidden", "yearNr"], null, "", this.selectedDate.getFullYear(), this.selectedYearEl);}
        this.yearSelect.appendChild(fragment);
    }


    addMonths() {
        let fragment = document.createDocumentFragment();
        // kuud lisatakse dünaamiliselt
        Object.keys(this.months).forEach((i) => {this.createNewChidldEl(fragment, Number(i), "li", null, ["month"], null, this.months[i]['name'], this.selectedDate.getMonth(), this.selectedMonthEl);});
        this.monthList.appendChild(fragment);
    }


// nädalapäaevad

    addWeekdays() {
        let fragment = document.createDocumentFragment();
        Object.keys(this.days).forEach((i) => {this.createNewChidldEl(fragment, i, "li", null, ["weekday"], null, this.days[i]);});
        this.dayList.appendChild(fragment);
    }


// kuu päeavde lisamine
    addMonthDays() {

        /* Meetod kuvab päevade arvu valitud kuus vastavalt valitud aastale.

          Kuvatakse kuupäevad koos esimesse ja viimasesse nädalasse kuuluvate eelmise ja järgmise kuu päevadega (kokku 35 elementi).

          - Eelmise kuu päevad (juhul, kui kuu ei alga esmaspäevaga): eelmises kuus on 28-31 päeva, seega on olemas päevad 23-31 ning iga kord kuvatakse neist sobivad.
          - Käesoleva kuu päevad: kuvatakse kõik kuupäevad vastavalt valitud kuule.
          - Järgmise kuu päevad (juhul, kui kuu ei lõpe pühapäevaga): makismaalselt kuus päeva järgmisest kuust, seega on olemas päevad 1-6 ning iga kord kuvatakse neist sobivad.

          */
        let testP = document.getElementById("test");

        let monthNr = this.selectedDate.getMonth();
        let yearNr = this.selectedDate.getFullYear();

        // päevade arv kuus
        let daysInMonth = this.months[monthNr]['days'];
        let weekdayOfFirst = new Date(yearNr, monthNr).getAdjustedDay();
        let weekdayOfLast =  new Date(yearNr, monthNr, daysInMonth).getAdjustedDay();


        //testP.innerText = "";
        //testP.innerText += " " + this.selectedDate;
        //testP.innerText += " " + daysInMonth;
        //testP.innerText += " " + new Date(yearNr, monthNr);
        //testP.innerText += " " + weekdayOfLast;

        let prevMonthDayEls = document.querySelectorAll('.prevMonthDay');
        for(var i = 0; i < prevMonthDayEls.length; i++){prevMonthDayEls[i].classList.add('hidden');}


        let nextMonthDayEls = document.querySelectorAll('.nextMonthDay');
        for(var i = 0; i < nextMonthDayEls.length; i++){nextMonthDayEls[i].classList.add('hidden');}

        // eelmise kuu päevade lisamine, kui kuu ei alga esmaspäevaga
        if (weekdayOfFirst !== 0) {

            // testP.innerText = "";
            //  testP.innerText += " " + monthNr;
            //testP.innerText += " " + this.months[monthNr - 1];

            // Jaanuaris on 31 päeva, muul juhul leitakse eelmise kuu päevade arv
            let daysInPrevMonth = (monthNr === 0 ? 31 : (monthNr === 3 ? new Date(yearNr, monthNr - 1, 0).getAdjustedDay() : this.months[monthNr - 1]['days']));


            for (let i = daysInPrevMonth - weekdayOfFirst + 1; i <= daysInPrevMonth; i++) {
                //testP.innerText += " I. " + i;

                // leitakse näidatav element
                let elToShow = document.querySelectorAll('.prevMonthDay[data-value="' + i + '"]')[0];

                if (elToShow) {
                    elToShow.classList.remove("hidden");
                    elToShow.classList.add("half-hidden");
                }

            }

        }


        // järgmise kuu päevade lisamine, kui kuu ei lõpe pühapäevaga
        if (weekdayOfLast !== 6) {
            // testP.innerText += " " + (6 - weekdayOfLast);

            for (let i =  1; i <= (6 - weekdayOfLast); i++) {
                //testP.innerText += " I. " + i;

                // leitakse näidatav element
                let elToShow = document.querySelectorAll('.nextMonthDay[data-value="' + i + '"]')[0];

                if (elToShow) {
                    elToShow.classList.remove("hidden");
                    elToShow.classList.add("half-hidden");
                }
            }
        }
    }
}





/* NAV Copyright (c) 2024 by Jeff Bredenkamp (https://codepen.io/jeffbredenkamp/pen/VypMNE) */

var navigation = new TimelineLite({paused:true, reversed:true});
navigation.to("#navigationWrap", 0.5, {opacity: 1, display: 'block'})
    .to(".navbar", 0.3, {opacity: 0}, "-=0.1")
    .to(".close", 0.3, {display: "block", opacity: 1}, "-=0.1")
    .from(".menu", 0.5, {opacity: 0, y: 30})
    .from(".social", 0.5, {opacity: 0});

document.querySelectorAll('.navbar, .close').forEach(function(element) {
    element.addEventListener('click', function() {
        navigation.reversed() ? navigation.play() : navigation.reverse();
    });
});



/* Adapted from Copyright (c) 2024 by abxlfazl khxrshidi (https://codepen.io/abxlfazl/pen/VwKzaEm) */

// Designed by:  Mauricio Bucardo
// Original image:
// https://dribbble.com/shots/5619509-Animated-Tab-Bar

class selectionMenu {

    constructor() {
        // Get menu elements
        this.menu = document.querySelector(".selection-menu");
        this.menuItems = this.menu.querySelectorAll(".menu__item");
        this.menuBorder = this.menu.querySelector(".menu__border");

        this.activeItem = this.menu.querySelector(".active"); // Set the initial active item
        this.itemOffsets = [];

        this.lastShownElId = "course-overview";



        // Add click event listeners to menu items
        this.menuItems.forEach((item, index) => {
            let rect = item.getBoundingClientRect();

            ;
            this.itemOffsets[index] = {
                left: rect.left,
                top: rect.top,
                right: rect.right,
                bottom: rect.bottom
            };
            item.addEventListener("click", () => {this.clickItem(item, index);
                let elId = item.getAttribute('data-el-id');



                if ((elId) && (elId !== this.lastShownElId)) {
                    showHiddenEl(elId);
                    showHiddenEl(this.lastShownElId);


                    this.lastShownElId = elId
                }
            });


        });

        // Initial positioning of the menu border
        this.offsetMenuBorder(this.itemOffsets[0]);

        // Update the menu border when the window is resized
        window.addEventListener("resize", () => {
            const activeIndex = Array.from(this.menuItems).indexOf(this.activeItem);
            this.offsetMenuBorder(this.itemOffsets[activeIndex], this.menuBorder);
            this.menu.style.setProperty("--timeOut", "none");
        });

    }


    clickItem(item, index)  { // Function to handle item click
        this.menu.style.removeProperty("--timeOut");

        // If the clicked item is already active, do nothing
        if (this.activeItem === item) return;

        // Remove the active class from the currently active item, if it exists
        if (this.activeItem) {
            this.activeItem.classList.remove("active");

        }

        // Add the active class to the clicked item and update the activeItem variable
        item.classList.add("active");
        this.activeItem = item;

        this.offsetMenuBorder(this.itemOffsets[index]);
    }

    // Function to update the position of the menu border

    offsetMenuBorder(offsets) {



        const left = Math.floor(offsets.left - this.menu.offsetLeft - (this.menuBorder.offsetWidth  - offsets.right + offsets.left) / 2) - 17.5 +  "px";

        this.menuBorder.style.transform = `translate3d(${left}, 0 , 0)`;
    }


}


/* KLASSIDE LOOMINE */
const selectionMenuEl = new selectionMenu(); // uue Calendar klassi objekti loomine

const calendar = new Calendar(); // uue Calendar klassi objekti loomine

let submitButton = document.getElementById("submitButton");

submitButton.addEventListener("click", () => {
    let elId = submitButton.getAttribute('data-el-id');

    showHiddenEl("sm");
    showHiddenEl(elId);
    showHiddenEl("userInput");
    revealResult();

});

showHiddenEl("sm");

document.getElementById("inputResMinimized").getElementsByTagName('button')[0].addEventListener("click", () => {showHiddenEl("userInput");});
document.getElementById("textInputBtn").addEventListener("click", () => {addTextInput()});
document.getElementById("urlInputBtn").addEventListener("click", () => {addUrlInput()});
document.getElementById("calInputBtn").addEventListener("click", () => {addCalInput()});
document.getElementById("binBtn").addEventListener("click", () => {deleteAllUserInput()});
document.getElementById('course-input-form').addEventListener('submit', function(event) {submitForm(event);});

let icalArray = [];