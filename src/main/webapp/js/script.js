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