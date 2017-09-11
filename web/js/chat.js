
$(document).ready(function ()
{
    /*
     declare gloabl box variable,
     so we can check if box is alreay open,
     when user click toggle button
     */
    var box = null;

    /*
     we are now adding click hanlder for 
     toggle button.
     */
    //$("input[type='button']").click(function(event, ui)
    $("#chatBarId").click(function (event, ui)
    {
        /*
         now if box is not null,
         we are toggling chat box.
         */
        if (box)
        {
            /*
             below code will hide the chatbox that 
             is active, when first clicked on toggle button
             */
            box.chatbox("option", "boxManager").toggleBox();
        }
        else
        {
            /*
             if box variable is null then we will create
             chat-box.
             */
            var userContent = "";
            box = $("#chat_div").chatbox(
                    {
                        /*
                         unique id for chat box
                         */
                        id: "Runnable",
                        //  id1:"HelpTeam",
                        user:
                                {
                                    key: "value"
                                },
                        /*
                         Title for the chat box
                         */
                      // title: '<span style="display:block; width:20%;" onclick="audioStart();" id="play"><span class="glyphicon glyphicon-headphones"></span> <span  class="glyphicon glyphicon-play" ></span></span>   <span><span class="glyphicon glyphicon-headphones"></span> <span onclick="audioStop();" class="glyphicon glyphicon-stop" ></span></span>',
                          // title: 'Runnable User ',
                          title: '<span style="display:inline-block" onclick="audioStart();" id="play"><span class="glyphicon glyphicon-headphones "></span> <span class="glyphicon glyphicon-play"></span></span>    <span style="display:none" onclick="audioStop();" id="stop"><span class="glyphicon glyphicon-headphones"></span> <span class="glyphicon glyphicon-stop" ></span></span>',
                        /*
                         messageSend as name suggest,
                         this will called when message sent.
                         and for demo we have appended sent message to our log div.
                         */
                        messageSent: function (id, user, msg)
                        {
                            //$("#log").append(id + " said: " + msg + "<br/>");
//                            userContent= msg;  
//                            var resApiResponse=["How are you?","Fine"];
                            if (msg != null) {
                                $("#chat_div").chatbox("option", "boxManager").addMsg(id, msg, "User");

                                $.post("/intent-ai-service/query-agent",
                                        {
                                            name: msg
                                        },
                                function (data, status) {
                                    msg = data.response;
                                    setTimeout(function () {

                                        $("#chat_div").chatbox("option", "boxManager").addMsg(id, msg, "Customer Care");

                                    }, 1000);
                                });
                            }
                        }
                    });
        }
    });
    
     
});