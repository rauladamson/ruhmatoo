

/* KALENDER */
Date.prototype.getAdjustedDay = function() {
    let day = this.getDay();
    return day === 0 ? 6 : day - 1;
};


/* Copyright (c) 2024 by Ivan Valentinov / jFog (https://codepen.io/jFog/pen/qBNZVyz) */

const tl = gsap.timeline({ paused: true, reversed: true });

tl.to(".wrapper", { // main on wrapperi sees, toimuvad stiilimuutused
    duration: 0.4, // kestvus 0.4s
    scale: 0.9, // main muudetakse väiksemaks: 90% tavasuurusest
    opacity: 0.3, // läbipaistvus on 0.3: 30% tavaläbipaistvusest
    filter: "blur(4px)" // filter muudab teravust: blur filter hägustab, tugevus 4px
}).from(
    ".ml-wrapper",
    { duration: 0.5,  // kestvus 0.5s
        opacity: 0, // nähtavus muutub 0-st kehtiva nähtavuseni
        ease: Power4.easeOut },"-=0.5" // animatsiooni algus kattub eelmise animatsiooniga
).from(
    ".ml-content",
    {
        duration: 0.5,  // kestvus 0.5s
        y: 140,
        scale: 2,
        filter: "blur(4px)",
        ease: Power4.easeOut},"-=0.5" // animatsiooni algus kattub eelmise animatsiooniga
);/*.from(
    ".ml-an-1",
    {
        duration: 0.2,  // kestvus 0.2s
        y: 40,
        stagger: 0.1,
        opacity: 0,
        scale: 1.2,
        ease: Power4.easeOut}, "-=0.6" // animatsiooni algus kattub eelmise animatsiooniga
);
*/
function addDelBtnListeners(buttons, type) {
    Array.from(buttons).forEach(delButton => {
        delButton.addEventListener('click', () => {


            let mainEl = document.getElementsByTagName("main")[0];
            let pElForEvent = document.getElementById("pElForEvent");
            let fnCall = delButton.getAttribute('data-fn-call');
//console.log(mainEl)
            if (tl.reversed()) {
                let optionalDelBtn = document.getElementById("optionalDelBtn");
                //console.log(optionalDelBtn)
                if (type === "one") {
                    optionalDelBtn.classList.remove("hidden")
                } else {
                    optionalDelBtn.classList.add("hidden")

                }

                mainEl.style["z-index"] = 0;
                pElForEvent.innerText = delButton.dataset.value;
                pElForEvent.dataset.uid = delButton.getAttribute('data-uid');
                tl.play()

            } else {
                if (fnCall !== null) {calendar.deleteEventObjs(pElForEvent.getAttribute('data-uid'), fnCall);}
                mainEl.style["z-index"] = 1;
                tl.reverse();
            }
        });
    })
}

/* Copyright (c) 2024 by Ivan Valentinov / jFog (https://codepen.io/jFog/pen/qBNZVyz) ENDS */

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

function createNewDropdownDiv(event) {

    let dropdownEl = document.createElement("div");
    dropdownEl.classList.add("dropdown");
    dropdownEl.classList.add("event-dropdown-el");
    dropdownEl.classList.add("column-flex");
    dropdownEl.dataset.uid = event.getUid();

    let dropdownElContentContainer = document.createElement("div");
    dropdownElContentContainer.innerText = event.getSummary() + " " + event.categories;
    dropdownElContentContainer.classList.add("dropdown-content-container");
    dropdownElContentContainer.style.backgroundColor = event.bgColor;


    let dropdownButton = document.createElement("button");
    dropdownButton.classList.add("dropbtn");

    // svg autor on zest: https://www.svgrepo.com/svg/509905/dropdown-arrow
    let dropdownButtonSvg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
    dropdownButtonSvg.classList.add("icon");
    dropdownButtonSvg.setAttribute("viewBox", "0 0 24 24");
    let path = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    path.setAttribute('d', "M12.7071 14.7071C12.3166 15.0976 11.6834 15.0976 11.2929 14.7071L6.29289 9.70711C5.90237 9.31658 5.90237 8.68342 6.29289 8.29289C6.68342 7.90237 7.31658 7.90237 7.70711 8.29289L12 12.5858L16.2929 8.29289C16.6834 7.90237 17.3166 7.90237 17.7071 8.29289C18.0976 8.68342 18.0976 9.31658 17.7071 9.70711L12.7071 14.7071Z");
    dropdownButtonSvg.appendChild(path);

    let eventDivEl = document.createElement("div");
    eventDivEl.classList.add("event-div");
    eventDivEl.classList.add("hidden");
    eventDivEl.innerText = event.description;

    let delButton = document.createElement('button');
    delButton.textContent = 'X';
    ///delButton.classList.add("delete-btn"); // lisatakse klass
    delButton.classList.add("cta"); // lisatakse klass
    delButton.dataset.value = event.getSummary() + " " + event.categories;
    delButton.dataset.value = event.getSummary() + " " + event.categories;
    delButton.dataset.uid = event.getUid();

    delButton.addEventListener('click', () => {

        console.log("click")
        let mainEl = document.getElementsByTagName("main")[0];
        let pElForEvent = document.getElementById("pElForEvent");


        if (tl.reversed()) {

            mainEl.style["z-index"] = 0;
            pElForEvent.innerText = delButton.dataset.value;
            pElForEvent.dataset.uid = delButton.getAttribute('data-uid');
            tl.play()

        }

    });

    //delButton.classList.add("hidden"); // lisatakse klass

    let editButton = document.createElement('button');
    editButton.textContent = 'E';
    editButton.classList.add("edit-btn"); // lisatakse klass
    //delButton.classList.add("hidden"); // lisatakse klass

    let buttonContainerDiv = document.createElement("div");
    buttonContainerDiv.classList.add("dropdown-btn-container"); // lisatakse klass

    dropdownButton.appendChild(dropdownButtonSvg);
    buttonContainerDiv.appendChild(delButton);
    buttonContainerDiv.appendChild(editButton);

    eventDivEl.appendChild(buttonContainerDiv);

    dropdownElContentContainer.appendChild(dropdownButton);
    dropdownEl.appendChild(dropdownElContentContainer);

    dropdownEl.appendChild(eventDivEl);


    dropdownButton.addEventListener('click', () => {eventDivEl.classList.toggle("hidden");}); // dropdownile vajutades alumine element avaneb/sulgub

    return dropdownEl;
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

    let form = document.getElementById("course-input-form-contaner");
    form.innerHTML = "";
}

function deleteEl(directParentEl) {

    if (directParentEl) {
        directParentEl.parentNode.removeChild(directParentEl);
    }
}


function showHiddenEl(elId) {

    //console.log(elId);
    let targetEl = document.getElementById(elId);
    //console.log(targetEl);
    //let userInputGridEl = document.getElementById("userInput");

    // peidab/näitab elementi
    targetEl.classList.toggle("hidden");

}

function submitForm(sendData) {
    return new Promise((resolve, reject) => {
        let xhr = new XMLHttpRequest();
        xhr.open('POST', 'inputServlet', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        xhr.onload = function() {
            if (this.status === 200) {
                let responseObj = JSON.parse(this.responseText);

                console.log(responseObj);
                if (responseObj.hasOwnProperty('course-input')) {

                    //console.log(true)
                    // Vaikimisi väärtuste taastamine
                    // Seda võiks mingil paremal viisil teha
                    // Tegelikult ei oleks seda üldse vaja, selle asemel peaks panema mingi asja, mis kontrollib kas aine juba on seal sees
                    // (hetkel muidu saaks ühte ainet mitu korda lisada)
                    let coCoursesList = document.getElementById('course-overview-valitud-kursused')
                    coCoursesList.innerHTML = 'Oled valinud järgmised kursused:';

                    document.getElementById('course-overview-ainepunktid').innerText =  'Ainepunkte kokku: ' + responseObj['ect-total'] + ' EAP'

                    // Ilmselt on mingi parem viis selle muutmiseks kui sedatüüpi otsene HTML-i muutmine?
                    responseObj['course-input'].forEach(function (oppeaine) {document.getElementById('course-overview-valitud-kursused').innerHTML += ' <a href="' + oppeaine['hetkeseVersiooniLink'] + '" target="_blank" rel="noopener noreferrer" >' + oppeaine['Nimi'] + '</a>, ';});

                }

                if (responseObj.hasOwnProperty('cal-input')) {
                    console.log(responseObj['cal-input'][0])

                    for (let i of responseObj['cal-input']) {
                        let calInput = JSON.parse(i);
                        calendar.iCalObjects.push( new iCalObj(calInput['uuid'], calInput['iCalLink'], calInput['events']));
                    }

                    if (calendar.initialized === false) {calendar.initialize()}
                    //document.getElementById("calendar-grid").classList.remove("hidden")
                }

                if (responseObj.hasOwnProperty('generate')) {

                    calendar.generateEvents();
                }

                console.log('Resolving the Promise with:', responseObj);

                resolve(responseObj);
            } else {
                reject(new Error('Request failed: ' + this.statusText));
            }
        }

        xhr.onerror = function() {
            reject(new Error('Network error'));
        };

        xhr.send(new URLSearchParams(sendData).toString());
    });
}


class iCalObj {
    constructor(uuid, iCalLink, eventsInput) {
        this.uuid = uuid;
        this.iCalLink = null;
        this.events = [];

        for (let event of eventsInput) { this.events.push(new CalendarEvent(event.uid, event.summary, event.location, event.description, event.categories, event.start, event.end, event.duration, event.occurrences, event.recurring))}

    }
    getEvents() {
        return this.events;
    }

    setEvents(newEvents) {
        this.events = newEvents;
    }

    removeEventWithUid(uid) {
        this.setEvents(this.getEvents().filter((event) => (event.getUid() !== uid)));
    }

    getEventByUid(uid, date) {
        return this.getEvents().filter((event) => (date == null ? event.getUid() === uid : null));
    }

    findEventsOnDate(eventList, day, month, year) {

        let suitableEventListInical = [];

        for (let event of eventList) {

            let eventoccurrencesInMonth = event.getOccurrences().filter(function(occurrence) {return ((day !== null ? occurrence.getDate() === day : true) && (month !== null ? occurrence.getMonth() === month : true) && (occurrence.getFullYear() === year));});
            //console.log(eventoccurrencesInMonth)
            if (eventoccurrencesInMonth.length > 0) {
                let occurenceKey = (day !== null ? "occurrencesOnDay" : "occurrencesInMonth");
                suitableEventListInical.push({"event": event,  [occurenceKey]: eventoccurrencesInMonth});
            }
        }

        return suitableEventListInical;

    }


}

class CalendarEvent {
    constructor(uid, summary, location, description, categories, start, end, duration, occurrences, recurring) {
        this.uid = uid;
        this.summary = summary;
        this.location = location;
        this.description = description;
        this.categories = categories;
        this.start = new Date(start.replace(" EEST", "").replace(" EET", ""));
        this.end = new Date(end.replace(" EEST", "").replace(" EET", ""));
        this.start = new Date(start);
        this.end = new Date(end);
        this.duration = duration;
        this.occurrences = [];
        this.recurring = recurring;

        let r = Math.floor(Math.random() * 256); // Random between 0-255
        let g = Math.floor(Math.random() * 256); // Random between 0-255
        let b = Math.floor(Math.random() * 256); // Random between 0-255
        this.bgColor = 'rgb(' + r + ',' + g + ',' + b + ')';

        if (occurrences) {for (let occurrence of occurrences) {this.occurrences.push(new Date(occurrence.replace(" EEST", "").replace(" EET", "")))}}

        //if (occurrences) {for (let occurrence of occurrences) {this.occurrences.push(new Date(occurrence))}}
        else {this.occurrences.push(this.start);}

    }
    getSummary() {
        return this.summary;
    }
    setEnd(end) {
        this.end = end;
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

    removeOccurrence(occurrence) {
        const index = this.occurrences.indexOf(occurrence);
        if (index > -1) {
            this.occurrences.splice(index, 1);
        }
    }

    isRecurring() {
        return this.recurring;
    }

    getUid() {
        return this.uid;
    }
}



// kalendri kood
class Calendar {
    constructor() {

        this.iCalObjects = [];
        //this.eventsInSelectedMonth = [];
        //this.monthDayEls = this.addMonthDays();
        this.currentDate = new Date(); // algväärtuseks käesolev kuupäev
        this.selectedDate = new Date(); // algväärtuseks käesolev kuupäev
        this.initialized = false;
        this.currentView = "monthView";
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
        this.yearSelect = document.querySelector(".yearSelect"); // aastad
        this.monthList = document.getElementById("monthList");

        // Add event listener to the parent element
        this.yearSelect.addEventListener('click', (event) => { this.selectYear(event.target);});
        this.monthList.addEventListener('click', (event) => {this.selectMonth(event.target);});


    }

    /* FUNKTSIOONID */

    initialize() {
        // funktsioonide väljakutsed initsialiseerimisel
        this.addYears(); // aastate lisamine (2024 +- 5)
        this.addWeekdays(); // nädalapäevade lisamine (E - P)
        this.addMonths(); // kuude lisamine (Jan - Dec)
        this.dayFragment = this.createDayEl();

        this.addWeekGrid();
        this.addYearGrid();
        this.addWeekGrid();
        this.selectedMonthEl = document.getElementsByClassName('month selected')[0];
        this.selectedYearEl = document.getElementsByClassName('yearNr selected')[0];
        this.selectMonth(this.selectedMonthEl);
        this.selectYear(this.selectedYearEl);
        this.initialized = true;


    }

    generateEvent() {
        let occurrenceDate = ""
        let endDate = ""
        return new CalendarEvent("", "", "", "", "õppimine", occurrenceDate, endDate, occurrenceDate - endDate, [occurrenceDate], false);
    }

    generateEvents() {
        console.log("generating events")
        //console.log(this.iCalObjects)

        if(this.currentView === "monthView") {
            console.log(this.findEvents(null, this.selectedDate.getMonth(), this.selectedDate.getFullYear()))
            console.log(this.generateEvent())
        } else if (this.currentView === "weekView") {
            console.log("week")
        }

    }


    addFragmentToDomEl(fragment, domEl) {
        //console.log(fragment)
        //console.log(domEl)
        // console.log(fragment)
        let currentEl = document.getElementById(domEl);
        currentEl.innerHTML = "";
        // currentEl.removeChild(currentEl.childNodes[0])
        currentEl.appendChild(fragment);
    }

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
                outerEl.classList.add("selected");
                outerEl.classList.remove("hidden");
                outerEl.classList.remove("half-hidden");
                elToAddChildTo = outerEl;
            } else if ((i === nrToCheck - 1) || (i === nrToCheck + 1)) {
                outerEl.classList.remove("hidden");
            }

        }
        if (outerClassListItems.includes("day")) {
            let newFrag = this.dayFragment.cloneNode(true);
            outerEl.appendChild(newFrag);
            //console.log(outerEl)
            //console.log(this.dayFragment.cloneNode(true))
        }

        parent.appendChild(outerEl);
        return outerEl;

    }

    findEvents(day, month, year) {
        let eventsInSelectedMonth = [];

        for (let iCal of this.iCalObjects) {
            eventsInSelectedMonth.push(...iCal.findEventsOnDate(iCal.getEvents(), day, month, year))
        }

        eventsInSelectedMonth.sort(function(a, b) {
            let dayDiff = a.event.getStart().getAdjustedDay() - b.event.getStart().getAdjustedDay() // esmalt sorteeritakse nädalapäeva järgi
            if (dayDiff !== 0) {return dayDiff;}
            return a.event.getStart().getHours() - b.event.getStart().getHours();  // ja siis algusaja järgi
        });

        return eventsInSelectedMonth;
    }

    addLeftColEl(monthIndex, type, el) {


        let dateElement = document.getElementById('leftColDateEl');

        let onetimeEvents = document.getElementById('onetime-events');
        let recurringEvents = document.getElementById('recurring-events');
        let leftColOverview = document.getElementById('leftColOverview');
        if (type === "monthView") {
            dateElement.innerText = el.innerText;
            let eventsInMonth = this.findEvents(null, monthIndex, this.selectedDate.getFullYear());
            leftColOverview.classList.remove("hidden")
            onetimeEvents.innerHTML = "";
            recurringEvents.innerHTML = "";

            let onetimeEventFragment = document.createDocumentFragment();
            let recurringEventFragment = document.createDocumentFragment();

            for (let eventKVPair of eventsInMonth) {
                let occurrencesInMonth = eventKVPair.occurrencesInMonth;
                ((occurrencesInMonth.length > 1) ? recurringEventFragment: onetimeEventFragment).appendChild(createNewDropdownDiv(eventKVPair.event,));
            }

            onetimeEvents.appendChild(onetimeEventFragment);
            recurringEvents.appendChild(recurringEventFragment);
        } else if (type === "dayView") {

            if (el.tagName === "A") {
                console.log(el.nextElementSibling)
                el = el.parentElement;

            }
            console.log(el)

            let dateEl = new Date(el.dataset.date);
            console.log(dateEl)
            dateElement.innerText = dateEl.getDate() + ". " + this.months[dateEl.getMonth()]['name']

            leftColOverview.classList.add("hidden")
            let dayOverview = document.getElementById('dayOverview');
            dayOverview.innerHTML = "";
            dayOverview.appendChild(el);


            Array.from(el.querySelectorAll('.dropbtn')).forEach(function(element) {
                element.addEventListener('click', function() {
                    element.parentElement.nextSibling.classList.toggle("hidden");
                });
            });
            addDelBtnListeners(el.querySelectorAll('.cta'), "one")

        }

    }

    // muuda valitud kuud
    selectMonth(el) {

        let oldSelectedMonthEl = document.getElementsByClassName('month selected')[0];
        if (oldSelectedMonthEl) {oldSelectedMonthEl.classList.remove("selected");}

        let monthIndex = Number(el.dataset.value);

        if (this.months[monthIndex] === undefined) {console.error(`Month index ${monthIndex} is undefined`);return;}

        if (this.currentView === "monthView") {
            this.addLeftColEl(monthIndex, "monthView", el);
        }

        el.classList.add("selected");
        this.selectedMonthEl = el;
        this.selectedDate.setMonth(Number(el.dataset.value));

        //console.log(Number(this.selectedDate.getMonth()), Number(this.selectedDate.getFullYear()))

        this.addFragmentToDomEl(this.updateMonthDays(Number(this.selectedDate.getMonth()), Number(this.selectedDate.getFullYear()), ["monthViewEl"]), "daysContainer");
        /*
        Uncaught TypeError: Cannot read properties of undefined (reading 'days')
    at Calendar.addMonthDays (script.js?830510671:522:109)
    at Calendar.selectMonth (script.js?830510671:390:14)
    at HTMLUListElement.<anonymous> (script.js?830510671:269:67)
         */
        // kui konkreetset kuupäeva pole valitud, siis kuvatakse kõik kuu sündmused

    }

    selectDay(el) { // funktsioon muudab valitud päeva

        /*let oldSelectedDayEl = document.getElementsByClassName('day selected')[0];
        oldSelectedDayEl.classList.remove("selected");*/

        //let dateDay = document.getElementById("dateDay");
        //dateDay.innerText = el.title;
        //el.classList.add("selected");

        this.selectedDate.setDate(Number(el.dataset.value));
        this.addLeftColEl(Number(el.dataset.value), "dayView", el.cloneNode(true))
        //el.classList.remove("selected");

    }


    selectYear(el) { // funktsioon muudab valitud aastat

        let selectedYearEl = document.getElementsByClassName('yearNr selected')[0];
        if (selectedYearEl) {
            selectedYearEl.classList.remove("selected");
            selectedYearEl.classList.add("half-hidden");
        }
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
        // TODO Potential bug:
        this.addFragmentToDomEl(this.updateMonthDays(Number(this.selectedDate.getMonth()), Number(this.selectedDate.getFullYear()), ["monthViewEl"]), "daysContainer");
    }

    addGroupOfEventsToDays(day, month, year, parentEl, classListItems) {
        //parentEl.innerHTML = "";

        let childDivs = parentEl.getElementsByTagName('div');
        while(childDivs[0]) {
            parentEl.removeChild(childDivs[0]);
        }

        let arrayOfEvents = this.findEvents(day, month, year);
        if (arrayOfEvents.length > 0) {
            if (classListItems.includes("yearViewEl")) {
                parentEl.classList.add("hasEvents");

            }
            for (let eventKVPair of arrayOfEvents) {
                let event = eventKVPair.event;

                for (let occurrence of eventKVPair.occurrencesOnDay) { // kõik toimumiskorrad päevas (juhuks, kui neid on rohkem kui üks) lisatakse kalendrisse

                    let eventStartHour = occurrence.getHours();
                    //if (eventStartHour < firstHour) {firstHour = eventStartHour}
                    //if (eventStartHour > lastHour) {lastHour = eventStartHour}

                    this.addEvent(event, parentEl.querySelectorAll('.dayHourDiv[data-value="' + eventStartHour + '"]')[0], classListItems, event.bgColor, occurrence);
                }
            }
        }
        if (this.currentView === "monthView") {
            for (let el of parentEl.querySelectorAll('.dayHourDiv:not(.hasEvent)')) {
                //let hourNr = Number(el.dataset.value);
                //if ((hourNr > lastHour) || (hourNr < firstHour)) {
                el.classList.add("hidden");
                // }

            }
        }

    }

    /*displayEvent(target) {
        console.log("clicked on target to display el")
    }*/

    // sündmuse kalendrisse lisamine
    addEvent(event, parent, classListItems, bgColor, occurrence) {

        let childDiv2 = createNewDropdownDiv(event)


        //let childDiv2 = document.createElement("div"); // luuakse uus div container
        childDiv2.style.backgroundColor = bgColor;
        for (let item of classListItems) {childDiv2.classList.add(item);}
        parent.classList.add("hasEvent");

        //childDiv2.addEventListener('click', (event) => {this.displayEvent(event.target);});

        parent.appendChild(childDiv2);
    }

    // kõigi alamelementide kustutamine
    /*removeAllChildNodes(parent) {
        while (parent.firstChild) {
            parent.removeChild(parent.firstChild);
        }
    }*/



    deleteEventObjs(uid, deleteType) {

        // one month all

        for (let iCal in this.iCalObjects){ // olemasolevate kalendrite iteratsioon
            //console.log(iCal.getEvents());
            for (let iCal of this.iCalObjects) {


                if (deleteType !== "all") { // kui tahetakse kustutada ainult üks sündmus või sündmused ühel kuul, siis
                    /*console.log(iCal.findEventsOnDate(deleteType == "one" ? this.selectedDate.getDate() : null,
                                                      this.selectedDate.getMonth(),
                                                      this.selectedDate.getYear()))


               */
                    let eventObj = iCal.getEventByUid(uid);
                    let occurrenceList = iCal.findEventsOnDate(eventObj, (deleteType === "one" ?this.selectedDate.getDate() : null), (deleteType === "month" ?this.selectedDate.getMonth() : null), this.selectedDate.getFullYear())[0];

                    if (occurrenceList != null) {
                        let occurrences = deleteType === "month" ? occurrenceList.occurrencesInMonth : occurrenceList.occurrencesOnDay;

                        occurrences.forEach((occurrence) => {
                            eventObj[0].removeOccurrence(occurrence);
                        });
                    }



                    // TODO kui event on tühi, siis küsida, kas taab occ lisada või eventi kustuta

                } else {iCal.removeEventWithUid(uid);}

                // TODO kui on 1, aga ongi ainult 1 event
                if (deleteType !== "one") {document.querySelectorAll('div[data-uid="' + uid + '"]')[0].remove();}
            }

        }

        this.addFragmentToDomEl(this.updateMonthDays(Number(this.selectedDate.getMonth()), Number(this.selectedDate.getFullYear()), ["monthViewEl"]), "daysContainer");
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

    createDayEl() {

        let dayFragment = document.createDocumentFragment();
        let dhl = document.createElement('ul');
        dhl.classList.add("dayHourList");
        dhl.classList.add("column-flex");



        for (let i=0; i < 24; i++) {
            this.createNewChidldEl(dhl, i, "li", "a", ["dayHourDiv"], ["dayHourDivA"], "")
        }


        //c//onsole.log(dayFragment)
        dayFragment.appendChild(dhl);
        return dayFragment;
    }

    addMonthDays(additionalClasses) {

        let daysList = document.createElement('div');
        daysList.classList.add("days");

        additionalClasses.forEach(item => daysList.classList.add(item));

        let dayElsFragment = document.createDocumentFragment();

        for (let i=23; i <= 31; i++) {this.createNewChidldEl(dayElsFragment, i, "div", "p", ["day", "prevMonthDay"], ["dayA"], "");}

        for (let i=1; i <= 31; i++) {this.createNewChidldEl(dayElsFragment, i, "div", "p", ["day"], ["dayA"], "");}

        for (let i=1; i <= 6; i++) {this.createNewChidldEl(dayElsFragment, i, "div", "p", ["day", "nextMonthDay"], ["dayA"], "");}



        daysList.appendChild(dayElsFragment);

        daysList.addEventListener('click', (event) => {
            this.selectDay(event.target);
        });

        return daysList;


    }
    addWeekGrid() {


        let newFragment = document.createDocumentFragment();
        let currentWeekday =  this.currentDate.getAdjustedDay();
        let currentMonthDate =  this.currentDate.getDate();




        let daysList = this.updateMonthDays(this.currentDate.getMonth(), this.currentDate.getFullYear() , ["weekGridEl"])


        for (let day of daysList.querySelectorAll('.day:not(.prevMonthDay):not(.nextMonthDay)')) {
            let dayNr = Number(day.dataset.value);
            if ((dayNr >= currentMonthDate - currentWeekday) && (dayNr <= (currentMonthDate + (6 - currentWeekday)))) {


                newFragment.appendChild(day);
            }
        }
        this.addFragmentToDomEl(newFragment, "weekGrid");


    }

    addYearGrid() {

        let monthGridElsFragment = document.createDocumentFragment();

        for (let i=0; i <12; i++) {

            monthGridElsFragment.appendChild(this.updateMonthDays(i, Number(this.selectedDate.getFullYear()), ["yearViewEl"]));



        }


        this.addFragmentToDomEl(monthGridElsFragment, "monthGrid");
        //  return monthGridElsList;


    }



// kuu päeavde lisamine
    updateMonthDays(monthNr, yearNr, additionalClassesList) {
        //console.log(monthNr, yearNr)


        /* Meetod kuvab päevade arvu valitud kuus vastavalt valitud aastale.

          Kuvatakse kuupäevad koos esimesse ja viimasesse nädalasse kuuluvate eelmise ja järgmise kuu päevadega (kokku 35 elementi).

          - Eelmise kuu päevad (juhul, kui kuu ei alga esmaspäevaga): eelmises kuus on 28-31 päeva, seega on olemas päevad 23-31 ning iga kord kuvatakse neist sobivad.
          - Käesoleva kuu päevad: kuvatakse kõik kuupäevad vastavalt valitud kuule.
          - Järgmise kuu päevad (juhul, kui kuu ei lõpe pühapäevaga): makismaalselt kuus päeva järgmisest kuust, seega on olemas päevad 1-6 ning iga kord kuvatakse neist sobivad.*/


        // See jookseb kokku teatud juhtudel, nt kui valida uus aasta ja siis seal mingi kuu.
        // Siis ei ole millegipärast selectedDate objekti olemas.
        let fragment = document.createDocumentFragment();


        let dayEls = this.addMonthDays(additionalClassesList); //this.monthDayEls//s.map(element => element.cloneNode(true));

        additionalClassesList.push("calendarEventDiv")
        let daysInMonth = (monthNr === 1 ? new Date(yearNr, monthNr + 1, 0).getDate() : this.months[monthNr]['days']);  // päevade arv kuus
        let weekdayOfFirst = new Date(yearNr, monthNr).getAdjustedDay();
        let weekdayOfLast =  new Date(yearNr, monthNr, daysInMonth).getAdjustedDay();
        let thisMonthEls = dayEls.querySelectorAll('.day:not(.prevMonthDay):not(.nextMonthDay)');


        for (let i = 1; i <= thisMonthEls.length; i++) {
            let thisMonthDayEl = thisMonthEls[i - 1];

            // kuvatakse algava kuu esimese nädalapäeva numbri ja esmaspäeva vahe võrra eelmise kuu elemente
            if (i <= daysInMonth){
                thisMonthDayEl.classList.remove("hidden");
                //console.log(new Date(yearNr, monthNr, i) + " " + monthNr)
                thisMonthDayEl.dataset.date = new Date(yearNr, monthNr, i);
                this.addGroupOfEventsToDays(i,monthNr,yearNr, thisMonthDayEl, additionalClassesList)

            } else {
                thisMonthDayEl.classList.add('hidden'); // esmalt peidetakse kõik eelmise kuu elemendid
            }
        }


        // kõik eelmise ja järgmise kuu päevad peidetakse
        let prevMonthDayEls = dayEls.querySelectorAll('.prevMonthDay');
        let nextMonthDayEls = dayEls.querySelectorAll('.nextMonthDay');
        let daysInPrevMonth = null;

        // eelmise kuu päevade lisamine, kui kuu ei alga esmaspäevaga: Jaanuaris on 31 päeva, muul juhul leitakse eelmise kuu päevade arv
        if (weekdayOfFirst !== 0) {
            daysInPrevMonth = (monthNr === 0 ? 31 : (monthNr === 2 ? new Date(yearNr, monthNr, 0).getDate() : this.months[monthNr - 1]['days'])); }


        for (let i = 0; i < prevMonthDayEls.length; i++) {
            let prevMonthDayEl = prevMonthDayEls[i];
            let elDayVal = Number(prevMonthDayEls[i].dataset.value);


            if ((daysInPrevMonth) && (elDayVal > (daysInPrevMonth - weekdayOfFirst)) && (elDayVal <= daysInPrevMonth)) {
                prevMonthDayEl.classList.remove("hidden");
                prevMonthDayEl.classList.add("half-hidden");
                let prevDate = (monthNr === 0 ? new Date(yearNr - 1, 11, elDayVal) : new Date(yearNr, monthNr - 1, elDayVal));
                prevMonthDayEl.dataset.date = prevDate;

                this.addGroupOfEventsToDays(elDayVal, prevDate.getMonth(), prevDate.getFullYear(), prevMonthDayEl, additionalClassesList)
            }
            else {
                delete prevMonthDayEl.dataset.date; // kuupäev eemaldatakse
                prevMonthDayEl.classList.add('hidden'); // esmalt peidetakse kõik eelmise kuu elemendid
            }

        }


        for (let i = 1; i <= nextMonthDayEls.length; i++){

            let nextMonthDayEl = nextMonthDayEls[i - 1];

            // kuvatakse algava kuu viimasenädalapäeva numbri ja pühapäeva vahe võrra eelmise kuu elemente
            //console.log(monthNr + " " + weekdayOfLast)
            if (i <= 6 - weekdayOfLast) {
                nextMonthDayEl.classList.remove("hidden");
                nextMonthDayEl.classList.add("half-hidden");
                let nextDate = (monthNr === 11 ? new Date(yearNr + 1, 0, i) : new Date(yearNr, monthNr + 1, i));
                nextMonthDayEl.dataset.date = nextDate;

                this.addGroupOfEventsToDays(i, nextDate.getMonth(), nextDate.getFullYear(), nextMonthDayEl, additionalClassesList)
            } else {
                delete nextMonthDayEl.dataset.date; // kuupäev eemaldatakse
                nextMonthDayEl.classList.add('hidden');
            }
        }


        if (additionalClassesList.includes("yearViewEl")) {
            let flexContainer = document.createElement('div');
            flexContainer.classList.add("column-flex");

            let monthNameText = document.createElement('h2');
            monthNameText.classList.add("monthNameText");

            monthNameText.innerText = this.months[monthNr]['name'];
            flexContainer.appendChild(monthNameText)
            flexContainer.appendChild(dayEls)
            fragment.appendChild(flexContainer)
        } else {
            fragment.appendChild(dayEls)
        }


        return fragment;
    }

    selectView(viewId) {

        //console.log(viewId);
        this.currentView = viewId;
        //console.log(this.currentView);
        let monthListEl = document.getElementById("monthList");
        let weekday = document.getElementsByClassName("weekday")[0];
        let yearEls = document.getElementById("yearEls");

        if (viewId === "yearView") {
            yearEls.classList.add("row-flex");
            monthListEl.classList.add("hidden");
            weekday.classList.add("hidden");
            document.getElementById("leftColEl").innerHTML = "";
        } else {
            yearEls.classList.remove("row-flex");
            monthListEl.classList.remove("hidden");
        }
        let viewEls = document.getElementsByClassName("calView");
        for (let view of viewEls) {
            if (view.id !== viewId) {view.classList.add("hidden");}
            else {view.classList.remove("hidden");}
        }
    }
}





/* NAV Copyright (c) 2024 by Jeff Bredenkamp (https://codepen.io/jeffbredenkamp/pen/VypMNE) */

let navigation = new TimelineLite({paused:true, reversed:true});
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
let calendar =  new Calendar(); // uue Calendar klassi objekti loomine
document.getElementById("inputResMinimized").getElementsByTagName('button')[0].addEventListener("click", () => {showHiddenEl("userInput");
    showHiddenEl("inputResMinimized")});
document.getElementById("textInputBtn").addEventListener("click", () => {addTextInput()});
document.getElementById("urlInputBtn").addEventListener("click", () => {

    addUrlInput()
});
document.getElementById("calInputBtn").addEventListener("click", () => {addCalInput();});
document.getElementById("binBtn").addEventListener("click", () => {deleteAllUserInput()});
document.getElementById('course-input-form').addEventListener('submit', function(event) {
    event.preventDefault();
    submitForm(new FormData(event.target))
        .then(responseObj => {
            // Handle the resolved promise here
            // For example, you can log the response object
            console.log(responseObj);
        })
        .catch(error => {
            // Handle any errors here
            console.error(error);
        });
    showHiddenEl("inputResMinimized")
    showHiddenEl("userInput");

});

let delButtons = document.getElementsByClassName("cta");
addDelBtnListeners(delButtons, "general");


//document.getElementById("downloadBtn").addEventListener('click', (event) => {submitForm({"mod-cal": JSON.stringify(calendar.iCalObjects[0])});});
document.getElementById("downloadBtn").addEventListener('click', (event) => {
    submitForm({"mod-cal": JSON.stringify(calendar.iCalObjects[0])})
        .then(responseObj => {
            // Start the download here
            window.location.href = "calendar.ics";
        })
        .catch(error => console.error(error));
});


document.getElementById("getLinkBtn").addEventListener('click', (event) => {
    submitForm({"generate": JSON.stringify(calendar.iCalObjects[0])})
        .then(responseObj => {
            // Start the download here
            //window.location.href = "calendar.ics";
            filename = responseObj["cal-url"];
            fullURL = document.location.origin+"/ics-files/"+filename;
            document.getElementById('iCalURL').innerHTML = 'iCal viide: <a href="' + fullURL + '">' + fullURL + '</a>';

        })
        .catch(error => console.error(error));
});

document.getElementById("generateBtn").addEventListener('click', (event) => {
    submitForm({"generate": JSON.stringify(calendar.iCalObjects[0])})
        .then(responseObj => {
            // Start the download here
            //window.location.href = "calendar.ics";
            console.log(responseObj);
        })
        .catch(error => console.error(error));
});


Array.from(document.getElementsByClassName('viewSelectorEl')).forEach(function(element) {

    element.addEventListener('click', function(event) {
        calendar.selectView(event.target.getAttribute('data-view-el-id'));
    });
});