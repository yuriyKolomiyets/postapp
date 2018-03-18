function loginUser() {
  var xhr = new XMLHttpRequest();
  xhr.open('POST', '/login', 'false');
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.onload = function() {
    if(xhr.status === 200) {
        alert(xhr.status);
    } else {
      alert(xhr.status);
    }
  }

  var email = $('#exampleInputEmail1').val();
  var pass = $('#exampleInputPassword1').val();
  var json = JSON.stringify({email: email, pass: pass});

  xhr.send(json);
}
// why not document.querySelector(.loginBtn)?
document.addEventListener("DOMContentLoaded", function(){

  var loginButton = document.getElementById('loginBtn');

  loginButton.addEventListener('click', function() {
    loginUser();
  })

})