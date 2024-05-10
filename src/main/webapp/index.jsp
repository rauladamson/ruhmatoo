<%@ page import="java.util.Random" %>
<%@ page import="jakarta.servlet.SessionCookieConfig" %>

<!DOCTYPE html>
    <html>

    <head>
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <%@ page contentType="text/html; charset=UTF-8" %>
            <title>Kollaboratiivne ajaplaneerimise programm</title>
    </head>

    <body>

    <header>


        <nav>
            <!--Copyright (c) 2024 by Jeff Bredenkamp (https://codepen.io/jeffbredenkamp/pen/VypMNE)-->
            <div class='navbar' title='Menu'>
                <i class="fa fa-bars" aria-hidden="true"></i>
            </div>
            <div class="close">
                <i class="fa fa-times" aria-hidden="true"></i>
            </div>
            <div id="navigationWrap">
                <div class="container menu">
                    <nav>
                        <ul>
                            <li><a href="#">...</a></li>
                            <li><a href="#">...</a></li>
                            <li><a href="#">...</a></li>
                            <li><a href="#">...</a></li>
                            <li><a href="#">...</a></li>
                        </ul>
                    </nav>
                    <div class="social">
                        <a href="https://codepen.io/collection/rxbyqV"><i class="fa fa-codepen" aria-hidden="true"></i></a>
                        <a href="https://github.com/rauladamson/ruhmatoo"><i class="fa fa-github" aria-hidden="true"></i></a>
                    </div>
                </div>
            </div>
        </nav>

        <h1>VEEBIRAKENDUS X</h1>
    </header>

    <main>

        <!-- Copyright (c) 2024 by abxlfazl khxrshidi (https://codepen.io/abxlfazl/pen/VwKzaEm)
-->

        <div id="sm" class="selection-menu-container">

            <menu class="selection-menu">

                <!-- Gabriele Malaspina: https://www.svgrepo.com/svg/489120/school -->
                <button class="menu__item active" data-el-id="course-overview">
                    <svg class="icon" viewBox="0 0 24 24">
                        <path d="M21 10L12 5L3 10L6 11.6667M21 10L18 11.6667M21 10V10C21.6129 10.3064 22 10.9328 22 11.618V16.9998M6 11.6667L12 15L18 11.6667M6 11.6667V17.6667L12 21L18 17.6667L18 11.6667"/>
                    </svg>
                </button>


                <!-- Icoon Mono: https://www.svgrepo.com/svg/479448/calendar-8 -->
                <button class="menu__item" data-el-id="calendar-grid">
                    <svg class="icon" viewBox="0 0 512 512" style="stroke-width: 10pt;">

                        <path class="st0" d="M164.893,89.791c13.875,0,25.126-11.243,25.126-25.134V25.118C190.019,11.252,178.768,0,164.893,0 s-25.135,11.252-25.135,25.118v39.538C139.758,78.548,151.018,89.791,164.893,89.791z"></path> <path class="st0" d="M350.184,89.791c13.867,0,25.126-11.243,25.126-25.134V25.118C375.31,11.252,364.05,0,350.184,0 c-13.875,0-25.135,11.252-25.135,25.118v39.538C325.048,78.548,336.309,89.791,350.184,89.791z"></path> <path class="st0" d="M437.25,35.807h-39.865v28.849c0,26.04-21.169,47.218-47.201,47.218c-26.032,0-47.209-21.178-47.209-47.218 V35.807h-90.881v28.849c0,26.04-21.178,47.218-47.2,47.218c-26.032,0-47.21-21.178-47.21-47.218V35.807H74.75 c-38.977,0-70.575,31.599-70.575,70.575v335.043C4.175,480.401,35.773,512,74.75,512H437.25c38.976,0,70.575-31.599,70.575-70.575 V106.382C507.825,67.406,476.226,35.807,437.25,35.807z M473.484,441.425c0,19.978-16.256,36.235-36.235,36.235H74.75 c-19.979,0-36.235-16.257-36.235-36.235V150.984h434.969V441.425z"></path> <rect x="174.928" y="382.512" class="st0" width="63.591" height="63.591"></rect> <rect x="174.928" y="283.96" class="st0" width="63.591" height="63.591"></rect> <rect x="76.385" y="382.512" class="st0" width="63.582" height="63.591"></rect> <rect x="76.385" y="283.96" class="st0" width="63.582" height="63.591"></rect> <rect x="372.032" y="185.417" class="st0" width="63.583" height="63.582"></rect> <rect x="273.48" y="185.417" class="st0" width="63.591" height="63.582"></rect> <polygon class="st0" points="350.041,293.216 331.127,278.51 296.686,322.811 276.238,306.454 261.273,325.142 300.677,356.673 "></polygon> <rect x="372.032" y="283.96" class="st0" width="63.583" height="63.591"></rect> <rect x="273.48" y="382.512" class="st0" width="63.591" height="63.591"></rect> <rect x="174.928" y="185.417" class="st0" width="63.591" height="63.582"></rect>  </svg>
                </button>

                <!-- Icoon Mono: https://www.svgrepo.com/svg/479448/calendar-8 -->
                <button class="menu__item" data-el-id="calendar-grid">
                    <svg class="icon" viewBox="0 0 512 512" style="fill: white;">

                        <path class="st0" d="M164.893,89.791c13.875,0,25.126-11.243,25.126-25.134V25.118C190.019,11.252,178.768,0,164.893,0 s-25.135,11.252-25.135,25.118v39.538C139.758,78.548,151.018,89.791,164.893,89.791z"></path> <path class="st0" d="M350.184,89.791c13.867,0,25.126-11.243,25.126-25.134V25.118C375.31,11.252,364.05,0,350.184,0 c-13.875,0-25.135,11.252-25.135,25.118v39.538C325.048,78.548,336.309,89.791,350.184,89.791z"></path> <path class="st0" d="M437.25,35.807h-39.865v28.849c0,26.04-21.169,47.218-47.201,47.218c-26.032,0-47.209-21.178-47.209-47.218 V35.807h-90.881v28.849c0,26.04-21.178,47.218-47.2,47.218c-26.032,0-47.21-21.178-47.21-47.218V35.807H74.75 c-38.977,0-70.575,31.599-70.575,70.575v335.043C4.175,480.401,35.773,512,74.75,512H437.25c38.976,0,70.575-31.599,70.575-70.575 V106.382C507.825,67.406,476.226,35.807,437.25,35.807z M473.484,441.425c0,19.978-16.256,36.235-36.235,36.235H74.75 c-19.979,0-36.235-16.257-36.235-36.235V150.984h434.969V441.425z"></path> <rect x="174.928" y="382.512" class="st0" width="63.591" height="63.591"></rect> <rect x="174.928" y="283.96" class="st0" width="63.591" height="63.591"></rect> <rect x="76.385" y="382.512" class="st0" width="63.582" height="63.591"></rect> <rect x="76.385" y="283.96" class="st0" width="63.582" height="63.591"></rect> <rect x="372.032" y="185.417" class="st0" width="63.583" height="63.582"></rect> <rect x="273.48" y="185.417" class="st0" width="63.591" height="63.582"></rect> <polygon class="st0" points="350.041,293.216 331.127,278.51 296.686,322.811 276.238,306.454 261.273,325.142 300.677,356.673 "></polygon> <rect x="372.032" y="283.96" class="st0" width="63.583" height="63.591"></rect> <rect x="273.48" y="382.512" class="st0" width="63.591" height="63.591"></rect> <rect x="174.928" y="185.417" class="st0" width="63.591" height="63.582"></rect>  </svg>
                </button>

                <!-- Solar Icons: https://www.svgrepo.com/svg/528951/download-minimalistic -->
                <button class="menu__item" >
                    <svg class="icon" viewBox="0 0 24 24">
                        <path d="M12 3V16M12 16L16 11.625M12 16L8 11.625"/>
                        <path d="M15 21H9C6.17157 21 4.75736 21 3.87868 20.1213C3 19.2426 3 17.8284 3 15M21 15C21 17.8284 21 19.2426 20.1213 20.1213C19.8215 20.4211 19.4594 20.6186 19 20.7487"/>
                    </svg>
                </button>

                <div class="menu__border"></div>

            </menu>

            <div class="svg-container">
                <svg viewBox="0 0 202.9 45.5" >
                    <clipPath id="menu" clipPathUnits="objectBoundingBox" transform="scale(0.0049285362247413 0.021978021978022)">
                        <path  d="M6.7,45.5c5.7,0.1,14.1-0.4,23.3-4c5.7-2.3,9.9-5,18.1-10.5c10.7-7.1,11.8-9.2,20.6-14.3c5-2.9,9.2-5.2,15.2-7
                      c7.1-2.1,13.3-2.3,17.6-2.1c4.2-0.2,10.5,0.1,17.6,2.1c6.1,1.8,10.2,4.1,15.2,7c8.8,5,9.9,7.1,20.6,14.3c8.3,5.5,12.4,8.2,18.1,10.5
                      c9.2,3.6,17.6,4.2,23.3,4H6.7z"/>
                    </clipPath>
                </svg>
            </div>

        </div>

        <!-- krystonschwarze: https://www.svgrepo.com/svg/510782/add-row -->


        <div id="inputResMinimized" class="hidden">
            <button data-el-id="userInput">

                <svg class="icon" viewBox="0 0 24 24"><path id="Vector" d="M3 14V15C3 16.1046 3.89543 17 5 17L19 17C20.1046 17 21 16.1046 21 15L21 13C21 11.8954 20.1046 11 19 11H13M10 8H7M7 8H4M7 8V5M7 8V11"></path></svg>
            </button>
        </div>
        <div class="flex-container">

            <div id="userInput" class="flex-container">





                <div class="grid-item">

                    <div class="button-container">
                        <button type="button" class="addElementButton" id="textInputBtn">
                            <span class="button_text2">AINEKOOD</span>
                            <span class="button__icon"><svg xmlns="http://www.w3.org/2000/svg" width="24"
                                                            viewBox="0 0 24 24" stroke-width="2" stroke-linejoin="round"
                                                            stroke-linecap="round" stroke="currentColor" height="24" fill="none"
                                                            class="svg">
                                        <line y2="19" y1="5" x2="12" x1="12"></line>
                                        <line y2="12" y1="12" x2="19" x1="5"></line>
                                    </svg></span>
                        </button>

                        <button type="button" class="addElementButton" id="urlInputBtn">
                            <span class="button_text2">URL</span>
                            <span class="button__icon"><svg xmlns="http://www.w3.org/2000/svg" width="24"
                                                            viewBox="0 0 24 24" stroke-width="2" stroke-linejoin="round"
                                                            stroke-linecap="round" stroke="currentColor" height="24" fill="none"
                                                            class="svg">
                                        <line y2="19" y1="5" x2="12" x1="12"></line>
                                        <line y2="12" y1="12" x2="19" x1="5"></line>
                                    </svg></span>
                        </button>

                        <button type="button" class="addElementButton" id="calInputBtn" >
                            <span class="button_text2">iCal</span>
                            <span class="button__icon"><svg xmlns="http://www.w3.org/2000/svg" width="24"
                                                            viewBox="0 0 24 24" stroke-width="2" stroke-linejoin="round"
                                                            stroke-linecap="round" stroke="currentColor" height="24" fill="none"
                                                            class="svg">
                                        <line y2="19" y1="5" x2="12" x1="12"></line>
                                        <line y2="12" y1="12" x2="19" x1="5"></line>
                                    </svg></span>
                        </button>


                        <button id="binBtn" class="bin-button" >
                            <svg class="bin-top" viewBox="0 0 39 7" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <line y1="5" x2="39" y2="5" stroke="white" stroke-width="4"></line>
                                <line x1="12" y1="1.5" x2="26.0357" y2="1.5" stroke="white" stroke-width="3"></line>
                            </svg>
                            <svg class="bin-bottom" viewBox="0 0 33 39" fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <mask id="path-1-inside-1_8_19" fill="white">
                                    <path d="M0 0H33V35C33 37.2091 31.2091 39 29 39H4C1.79086 39 0 37.2091 0 35V0Z">
                                    </path>
                                </mask>
                                <path
                                        d="M0 0H33H0ZM37 35C37 39.4183 33.4183 43 29 43H4C-0.418278 43 -4 39.4183 -4 35H4H29H37ZM4 43C-0.418278 43 -4 39.4183 -4 35V0H4V35V43ZM37 0V35C37 39.4183 33.4183 43 29 43V35V0H37Z"
                                        fill="white" mask="url(#path-1-inside-1_8_19)"></path>
                                <path d="M12 6L12 29" stroke="white" stroke-width="4"></path>
                                <path d="M21 6V29" stroke="white" stroke-width="4"></path>
                            </svg>
                        </button>


                    </div>

                    <form action="" method="get" id="course-input-form">
                        <div id="course-input-form-contaner" class="flex-container">

                        </div>

                        <button id="submitButton"class="customButton1" type="submit" data-el-id="inputResMinimized">
                            <a href="#"><span>VAATA</span></a>
                        </button>


                    </form>

                </div>
            </div>

            <div class="hidden" id="course-overview">


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





            <div class=" hidden" id="calendar-grid">
                <!--Copyright (c) 2024 by Alex Oliver (https://codepen.io/alexoliverwd/pen/kKQgwo)-->
                <div class="calendar">

                    <div class="col leftCol">
                        <div class="content">
                            <h1 class="date"><span id="dateMonth" class="inline-element"></span>
                                <span id="dateDay"></span>
                            </h1>
                            <div class="overview">
                                <div class="event-dropdown-container" ><h4>KORDUV</h4><div class="event-dropdown" id="recurring-events"></div></div>
                                <div class="event-dropdown-container"><h4>ÜHEKORDNE</h4><div class="event-dropdown"  id="onetime-events"></div></div>
                                <!--<p>
                                    <input type="text" value="" placeholder="new note"/>
                                    <a href="#" title="Add note" class="addNote animate">+</a>
                                </p>
                                <ul class="noteList">
                                    <li>Headbutt a lion<a href="#" title="Remove note" class="removeNote animate">x</a></li>
                                </ul>-->
                            </div>
                        </div>
                    </div>

                    <div class="col rightCol">
                        <div class="content">

                            <div id = "yearMonthContainer">
                                <ul id="monthList" class="months"></ul>
                                <div id ="yearEls" class="yearSelect"></div>
                            </div>
                            <ul class="weekday"></ul>
                            <div class="days">
                                <div title="23-prev" data-value="23" class="day prevMonthDay hidden"><a href="#" class="dayA">23</a></div>
                                <div title="24-prev" data-value="24" class="day prevMonthDay hidden"><a href="#" class="dayA">24</a></div>
                                <div title="25-prev" data-value="25" class="day prevMonthDay hidden"><a href="#" class="dayA">25</a></div>
                                <div title="26-prev" data-value="26" class="day prevMonthDay hidden"><a href="#" class="dayA">26</a></div>
                                <div title="27-prev" data-value="27" class="day prevMonthDay hidden"><a href="#" class="dayA">27</a></div>
                                <div title="28-prev" data-value="28" class="day prevMonthDay hidden"><a href="#" class="dayA">28</a></div>
                                <div title="29-prev" data-value="29" class="day prevMonthDay hidden"><a href="#" class="dayA">29</a></div>
                                <div title="30-prev" data-value="30" class="day prevMonthDay hidden"><a href="#" class="dayA">30</a></div>
                                <div title="31-prev" data-value="31" class="day prevMonthDay hidden"><a href="#" class="dayA">31</a></div>
                                <div title="1" data-value="1" class="day"><a href="#" class="dayA">1</a></div>
                                <div title="2" data-value="2" class="day"><a href="#" class="dayA">2</a></div>
                                <div title="3" data-value="3" class="day"><a href="#" class="dayA">3</a></div>
                                <div title="4" data-value="4" class="day"><a href="#" class="dayA">4</a></div>
                                <div title="5" data-value="5" class="day"><a href="#" class="dayA">5</a></div>
                                <div title="6" data-value="6" class="day"><a href="#" class="dayA">6</a></div>
                                <div title="7" data-value="7" class="day"><a href="#" class="dayA">7</a></div>
                                <div title="8" data-value="8" class="day"><a href="#" class="dayA">8</a></div>
                                <div title="9" data-value="9" class="day"><a href="#" class="dayA">9</a></div>
                                <div title="10" data-value="10" class="day"><a href="#" class="dayA">10</a></div>
                                <div title="11" data-value="11" class="day"><a href="#" class="dayA">11</a></div>
                                <div title="12" data-value="12" class="day"><a href="#" class="dayA">12</a></div>
                                <div title="13" data-value="13" class="day"><a href="#" class="dayA">13</a></div>
                                <div title="14" data-value="14" class="day"><a href="#" class="dayA">14</a></div>
                                <div title="15" data-value="15" class="day"><a href="#" class="dayA">15</a></div>
                                <div title="16" data-value="16" class="day"><a href="#" class="dayA">16</a></div>
                                <div title="17" data-value="17" class="day"><a href="#" class="dayA">17</a></div>
                                <div title="18" data-value="18" class="day"><a href="#" class="dayA">18</a></div>
                                <div title="19" data-value="19" class="day"><a href="#" class="dayA">19</a></div>
                                <div title="20" data-value="20" class="day"><a href="#" class="dayA">20</a></div>
                                <div title="21" data-value="21" class="day"><a href="#" class="dayA">21</a></div>
                                <div title="22" data-value="22" class="day"><a href="#" class="dayA">22</a></div>
                                <div title="23" data-value="23" class="day"><a href="#" class="dayA">23</a></div>
                                <div title="24" data-value="24" class="day"><a href="#" class="dayA">24</a></div>
                                <div title="25" data-value="25" class="day"><a href="#" class="dayA">25</a></div>
                                <div title="26" data-value="26" class="day"><a href="#" class="dayA">26</a></div>
                                <div title="27" data-value="27" class="day"><a href="#" class="dayA">27</a></div>
                                <div title="28" data-value="28" class="day"><a href="#" class="dayA">28</a></div>
                                <div title="29" data-value="29" class="day"><a href="#" class="dayA">29</a></div>
                                <div title="30" data-value="30" class="day"><a href="#" class="dayA">30</a></div>
                                <div title="31" data-value="31" class="day"><a href="#" class="dayA">31</a></div>
                                <div title="1-next" data-value="1" class="day nextMonthDay hidden"><a href="#" class="dayA">1</a></div>
                                <div title="2-next" data-value="2" class="day nextMonthDay hidden"><a href="#" class="dayA">2</a></div>
                                <div title="3-next" data-value="3" class="day nextMonthDay hidden"><a href="#" class="dayA">3</a></div>
                                <div title="4-next" data-value="4" class="day nextMonthDay hidden"><a href="#" class="dayA">4</a></div>
                                <div title="5-next" data-value="5" class="day nextMonthDay hidden"><a href="#" class="dayA">5</a></div>
                                <div title="6-next" data-value="6" class="day nextMonthDay hidden"><a href="#" class="dayA">6</a></div>
                            </div>
                        </div>
                    </div>


                </div>
            </div>

        </div>

    </main>

    <footer>
        <p>&copy; Marielin Kepp 2024</p>
    </footer>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.9.1/gsap.min.js"></script>
    <script src="./js/script.js?<%= (new Random()).nextInt()  %>"></script>

    <%-- https://stackoverflow.com/questions/7413234/how-to-prevent-caching-of-my-javascript-file --%>
    <%-- see vajalik sest kui ma muudatusi teinud ei laadinud brauser kunagi uut versiooni. Ma ei tea kas see on hea lahendus --%>


    </body>

    </html>