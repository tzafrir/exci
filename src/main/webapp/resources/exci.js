var sentenceArray = [];
var wordArray = [];
var theString = ""
function createGlobalVar(files){
	var f = files[0];
	if (f){
		var r = new FileReader();
		r.onload = function(e){
			theString = e.target.result;
			sentenceArray = theString.replace(/\n/g, " ").split(".");
		    var i;
		    for (i = 0; i < sentenceArray.length; i++) {
		    	sentenceArray[i] = sentenceArray[i].trim();
          if (!sentenceArray[i].length) {
            continue;
          }
		    	wordArray = wordArray.concat(sentenceArray[i].split(" "));
		    }
		}
		r.readAsText(f);
	}
}
function createPuzzle(wordArray){
	 
}

function shuffle(o){
    for(var j, x, i = o.length; i; j = Math.floor(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
    return o;
}

function createExercises(){
  var button = $('#create input');
  var dots = 0;
  var intervalId = setInterval(function(){
    var text = "Loading.";
    for (var i=0; i < dots; i++) {
      text += '.';
    }
    dots = (dots + 1) % 3;
    button.attr('value', text);
  }, 500);
	$.getJSON("/exercises?sentences="+JSON.stringify(sentenceArray)).done(function(data) {
    clearInterval(intervalId);
    $("#create, #main >").slideUp();
    var result = $("<div class='result'>");
    data.forEach(function(ex) {
      switch (ex.kind) {
        case 'mix_words':
          var line = $("<div class='exercise mix_words'>")
              .append("<label>Arrange the words to create a sentence</label>");
          ex.sentences.forEach(function(s){
            line.append("<div>"+s);
          });
          result.append(line);
          break;
        case 'choose_correct_verb':
          var line = $("<div class='exercise'>").addClass(ex.kind)
              .append("<label>Find the missing verb")
              .append("<div>"+ex.sentences[0]);
          var ol = $("<ol>").appendTo(line);
          ex.verbs[0].forEach(function(v){
            ol.append("<li>"+v);
          });
          result.append(line);
          break;
        case 'identify_picture':
          var line = $("<div class='exercise'>").addClass(ex.kind)
              .append("<label>Identify the picture")
              .append("<img src='" + ex.images[0] +"'>");
          var ol = $("<ol>").appendTo(line);
          shuffle(ex.sentences).forEach(function(v){
            ol.append("<li>"+v);
          });
          result.append(line);
          break
        case 'identify_word':
          var line = $("<div class='exercise'>").addClass(ex.kind)
              .append("<label>Identify the word")
              .append("<div>"+ex.sentences[0]);
          var ol = $("<ol>").appendTo(line);
          shuffle(ex.images).forEach(function(i){
            ol.append("<img src='" + i +"'>");
          });
          result.append(line);
          break

      }
    });
    $("#main").append(result);
  });
}