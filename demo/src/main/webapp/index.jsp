<html>
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

            <form action="#" th:action="@{/greeting}" th:object="${greeting}" method="post">
                <p>Id: <input type="text" th:field="*{id}" /></p>
                <p>Message: <input type="text" th:field="*{content}" /></p>
                <p><input type="submit" value="Submit" /> <input type="reset" value="Reset" /></p>
            </form>

            <form action="#" th:action="@{/userInput}" th:object="${userInput}" method="post" id="course-input-form">
                <div id="course-input-form-contaner">
                </div>
                <div>
                    <input type="submit" value="VAATA" />
                </div>
            </form>

        </div>

    </div>

    <script src="./js/script.js"></script>

</body>

</html>