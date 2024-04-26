<html>
<head>
    <link rel="stylesheet" type="text/css" href="../src/main/webapp/css/styles.css">
</head>
<body>

<header>
    <h1>VEEBIRAKENDUS X</h1>
</header>

<!--<main>-->

<div class="grid-container">
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
                <ul class="yearSelect"></ul>
                <ul id="monthList" class="months">
                </ul>
                <div class="clearfix"></div>
                <ul class="weekday"></ul>
                <div class="clearfix"></div>
                <ul class="days"></ul>
                <div class="clearfix"></div>
            </div>
        </div>

        <div class="clearfix"></div>

    </div>
</div>
<footer>
    <p>&copy; Marielin Kepp 2024</p>
</footer>


<script src="./js/script.js"></script>

</body>

</html>