export function sendGet(url, success) {
  const xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState == 4) {
      success(JSON.parse(xhr.responseText));
    }
  };
  xhr.open("GET", url);
  xhr.setRequestHeader("Accept", "application/json");
  xhr.send();
}
