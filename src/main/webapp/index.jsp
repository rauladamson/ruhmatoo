<%@ page import="java.util.Random" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <%@ page contentType="text/html; charset=UTF-8" %>
    <title>Kollaboratiivne ajaplaneerimise programm</title>
</head>
<body>

    <header>
        <h1>VEEBIRAKENDUS X</h1>
    </header>

    <main>

        <div class="grid-container">

            <div class="subgrid-item hidden" id="course-overview">
                <div>


                    <div>
                        <p>Ainepunkte kokku: </p>
                        <p>Oled valinud järgmised kursused: </p>
                    </div>

                    <div>
                        <p>Kursuse tööjaotus kokku (joonis hiljem): </p>
                    </div>

                    <div>
                        <p>Ainekavad nädala kaupa: </p>
                    </div>

                    <div>
                        <p>Tutvu kursustega ühekaupa (ÕISist info): </p>
                    </div>

                    <div>
                        <p>Teiste tudengite tagasiside kursusele: </p>
                    </div>
                </div>
            </div>

            <div class="subgrid-item initial" id="userInput">

                <button class="customButton1" type="button" onclick="showHiddenEl('course-overview')">
                    <a href="#"><span>ÕPPEAINED</span></a>
                </button>

                <button class="customButton1" type="button" onclick="showHiddenEl('calendar-grid')">
                    <a href="#"><span>KALENDER</span></a>
                </button>

                <div  class="grid-item">

                    <div class="button-container">
                        <button type="button" class="addElementButton" onclick="addTextInput()">
                            <span class="button_text2">AINEKOOD</span>
                            <span class="button__icon"><svg xmlns="http://www.w3.org/2000/svg" width="24" viewBox="0 0 24 24" stroke-width="2" stroke-linejoin="round" stroke-linecap="round" stroke="currentColor" height="24" fill="none" class="svg"><line y2="19" y1="5" x2="12" x1="12"></line><line y2="12" y1="12" x2="19" x1="5"></line></svg></span>
                        </button>

                        <button type="button" class="addElementButton" onclick="addUrlInput()">
                            <span class="button_text2">URL</span>
                            <span class="button__icon"><svg xmlns="http://www.w3.org/2000/svg" width="24" viewBox="0 0 24 24" stroke-width="2" stroke-linejoin="round" stroke-linecap="round" stroke="currentColor" height="24" fill="none" class="svg"><line y2="19" y1="5" x2="12" x1="12"></line><line y2="12" y1="12" x2="19" x1="5"></line></svg></span>
                        </button>

                        <button type="button" class="addElementButton" onclick="addCalInput()">
                            <span class="button_text2">iCal</span>
                            <span class="button__icon"><svg xmlns="http://www.w3.org/2000/svg" width="24" viewBox="0 0 24 24" stroke-width="2" stroke-linejoin="round" stroke-linecap="round" stroke="currentColor" height="24" fill="none" class="svg"><line y2="19" y1="5" x2="12" x1="12"></line><line y2="12" y1="12" x2="19" x1="5"></line></svg></span>
                        </button>



                        <!--<button onclick="addTextInput()">Lisa ainekood</button>
                        <button onclick="addUrlInput()">Lisa url</button>
                        <button onclick="deleteAllUserInput()">Kustuta kõik</button>-->

                        <button class="bin-button" onclick="deleteAllUserInput()">
                            <svg
                                    class="bin-top"
                                    viewBox="0 0 39 7"
                                    fill="none"
                                    xmlns="http://www.w3.org/2000/svg"
                            >
                                <line y1="5" x2="39" y2="5" stroke="white" stroke-width="4"></line>
                                <line
                                        x1="12"
                                        y1="1.5"
                                        x2="26.0357"
                                        y2="1.5"
                                        stroke="white"
                                        stroke-width="3"
                                ></line>
                            </svg>
                            <svg
                                    class="bin-bottom"
                                    viewBox="0 0 33 39"
                                    fill="none"
                                    xmlns="http://www.w3.org/2000/svg"
                            >
                                <mask id="path-1-inside-1_8_19" fill="white">
                                    <path
                                            d="M0 0H33V35C33 37.2091 31.2091 39 29 39H4C1.79086 39 0 37.2091 0 35V0Z"
                                    ></path>
                                </mask>
                                <path
                                        d="M0 0H33H0ZM37 35C37 39.4183 33.4183 43 29 43H4C-0.418278 43 -4 39.4183 -4 35H4H29H37ZM4 43C-0.418278 43 -4 39.4183 -4 35V0H4V35V43ZM37 0V35C37 39.4183 33.4183 43 29 43V35V0H37Z"
                                        fill="white"
                                        mask="url(#path-1-inside-1_8_19)"
                                ></path>
                                <path d="M12 6L12 29" stroke="white" stroke-width="4"></path>
                                <path d="M21 6V29" stroke="white" stroke-width="4"></path>
                            </svg>
                        </button>


                    </div>

                    <form action="" method="get" id="course-input-form" onsubmit="submitForm(event)">
                        <div id="course-input-form-contaner">

                        </div>
                        <!--<div>
                          <input type="submit" value="VAATA"/>
                        </div>-->

                        <button class="customSubmitButton" onclick="revealResult()">
              <span class="button_lg">
                <span class="button_sl"></span>
                 <span class="button_text">VAATA</span>
              </span>
                        </button>


                    </form>

                </div>
            </div>

            <div id="result"></div>
            <div class="subgrid-item hidden" id="calendar-grid">
                <!--Copyright (c) 2024 by Alex Oliver (https://codepen.io/alexoliverwd/pen/kKQgwo)-->
                <div class="calendar">

                    <div class="col leftCol">
                        <div class="content">
                            <h1 class="date">Friday<span id="dateMonth" class="inline-element"></span>
                                <span id="dateDay">1</span>
                            </h1>
                            <div class="notes">
                                <p>
                                    <input type="text" value="" placeholder="new note"/>
                                    <a href="#" title="Add note" class="addNote animate">+</a>
                                </p>
                                <ul class="noteList">
                                    <li>Headbutt a lion<a href="#" title="Remove note" class="removeNote animate">x</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="col rightCol">
                        <div class="content">
                            <p id="test"></p>
                            <!--<ul class="yearSelect"></ul>-->
                            <div class="yearSelect"></div>
                            <ul id="monthList" class="months">
                            </ul>
                            <div class="clearfix"></div>
                            <ul class="weekday"></ul>
                            <div class="clearfix"></div>
                            <div class="days"></div>
                            <!--<ul class="days"></ul>-->
                            <div class="clearfix"></div>
                        </div>
                    </div>

                    <div class="clearfix"></div>

                </div>
            </div>
            </div>
    </main>

    <footer>
        <p>&copy; Marielin Kepp 2024</p>
    </footer>


<script src="./js/script.js?<%= (new Random()).nextInt()  %>"></script>
    <%-- https://stackoverflow.com/questions/7413234/how-to-prevent-caching-of-my-javascript-file --%>
    <%-- see vajalik sest kui ma muudatusi teinud ei laadinud brauser kunagi uut versiooni. Ma ei tea kas see on hea lahendus --%>

</body>

</html>