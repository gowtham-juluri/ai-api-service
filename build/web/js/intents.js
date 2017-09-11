// this is the entry point and it is not hashboosted so
// 0. DO NOT CHANGE IT
// 1. RENAME it each time you actually change it
// 2. keep it as small as possible, all initializations should be done in the
// app module, not there
(function (window) {
  'use strict';
  jQuery(document).ready(function () {  
   var dynamicId=1;
  $('#intentCreate').click(function(event) {
     var name=$('#entityName').val();
     var userSays=$('#userSaysId').val();
     var textMessage=$('#textMessage').val();
     var actionValue=$('#actionId').val();
     var tableList=[];
     var items = new Object();
     var i=1;
     var isReqId=1;
     var isListId=1;
    $('#intentParametersTable tbody tr td').each( function(){
        $(this).find("input").each(function() {
            if(this.name=='is-required'){
                items["isRequired"]=$('#isRequired'+isReqId).is(":checked");
                isReqId=isReqId+1;
            }
            if(this.name=='actionParameter')
                items.paramName=this.value;
            if(this.name=='actionName')
                items.entityName=this.value;
            if(this.name=='actionValue')
                items.entityValue=this.value;
            if(this.name=='is-list'){
                items["isList"]=$('#isList'+isListId).is(":checked");
                isListId=isListId+1;
            }
         });
        //items.push( $(this).text() );  
        if(i%6==0){
        tableList.push(items);
         items = new Object();
        }
        i++;     
    });
    console.log(tableList);
    var json = {};
    var jsonObj = [];
    //var formdata=new FormData($('form')[0]);
    var jsonData = {
        "intent": name, 
        "userSays": userSays,
        "textMessage": textMessage,
        "action": actionValue,
        "params": tableList
        }
    jsonObj.push(jsonData);          
      $.post("/intent-ai-service/store-db",
                        {name: JSON.stringify(jsonData)
                        },
                        function(data, status){       
                        });
   });
   
      $("#tranningContentSave").click(function(){
         var formdata=new FormData($('form')[0]);
         $.ajax({
            url: "/intent-ai-service/store-train-data",
            type: "POST",
            data: formdata,
            mimeTypes:"multipart/form-data",
            contentType: false,
            cache: false,
            processData: false,
            success: function(){                   
                        
             },error: function(){
                    //alert("okey");
               }
         });
      });                
    $("#intentAddParam").click(function(){
     dynamicId=dynamicId+1;
            $("#intentParametersTable").append('<tr class="row" > <td class="col-sm-1"  ></td><td class="col-sm-1" ><input id="isRequired'+dynamicId+'" type="checkbox" name="is-required"> </td>  <td class="col-sm-3" ><input placeholder="Enter name" type="text" name="actionParameter" ></td>  <td class="col-sm-3" ><input placeholder="Enter entity" type="text" name="actionName" ></td>  <td class="col-sm-3" ><input placeholder="Enter value" type="text" name="actionValue"></td>  <td  class="col-sm-1"><input placeholder="Enter entity" type="checkbox" name="is-list" id="isList'+dynamicId+'"></td></tr>');
        });
        
        
        
      });
      
      
})(window);