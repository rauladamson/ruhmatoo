<html>
<<<<<<< HEAD
    <head>
        <link rel="stylesheet" type="text/css" href="./css/styles.css">
    </head>
<body>

    <div class="grid-container">


        <div id="landing" class="grid-item">
            <h1>Tere!</h1>
            <p>siia tuleb mingi tutvustav tekst</p>
        </div>

        <div id="userInput" class="grid-item">

            <div class="button-container">
                <button onclick="addTextInput()">Lisa ainekood</button>
                <button onclick="addUrlInput()">Lisa url</button>
                <button onclick="deleteAllUserInput()">Kustuta kõik</button>
            </div>

            <form action="inputServlet" method="post" id="course-input-form" onsubmit="submitForm(event)">
                <div id="course-input-form-contaner">
                </div>
                <div>
                    <input type="submit" value="VAATA" />
                </div>
            </form>

        </div>

        <div id="result">

=======
<head>
    <title>OOP projekt</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="./css/styles.css">
</head>
<body>

<div class="grid-container">


    <div id="landing" class="grid-item">
        <h1>Tere!</h1>
        <p>siia tuleb mingi tutvustav tekst</p>
    </div>

    <div id="userInput" class="grid-item">

        <div class="button-container">
            <button onclick="addTextInput()">Lisa ainekood</button>
            <button onclick="addUrlInput()">Lisa url</button>
            <button onclick="deleteAllUserInput()">Kustuta kõik</button>
        </div>

        <form action="inputServlet" method="post" id="course-input-form" onsubmit="submitForm(event)">

            <div id="course-input-form-contaner">
            </div>
            <div>
                <input type="submit" value="VAATA" />
            </div>
        </form>

        <div id="result">


>>>>>>> 5b285eb (Add untracked files)
        </div>

    </div>

<<<<<<< HEAD
    <script src="./js/script.js"></script>
=======
</div>

<script src="./js/script.js"></script>
>>>>>>> 5b285eb (Add untracked files)

</body>

</html>