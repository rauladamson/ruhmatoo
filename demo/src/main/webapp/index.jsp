<html xmlns:th="https://www.thymeleaf.org">
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

            <form action="./java/com.ut.servlet.inputServlet" method="post" id="course-input-form">
                <!--<form action="#" th:action="@{/userInput}" th:object="${userInput}" method="post" id="course-input-form">-->

                <div id="course-input-form-contaner">
                </div>
                <div>
                    <input type="submit" value="VAATA" />
                </div>
            </form>

            <h1>Result</h1>
            <p th:text="'content: ' + ${userInput.content}" />
            <a href="/userInput">Submit another message</a>

        </div>

    </div>

    <script src="./js/script.js"></script>

</body>

</html>