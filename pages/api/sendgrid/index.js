import sendgrid from "@sendgrid/mail";

sendgrid.setApiKey(process.env.SENDGRID_API_KEY);

async function sendEmail(req, res) {
  try {
    // console.log("REQ.BODY", req.body);
    await sendgrid.send({
      to: `${req.body.email}`, // Your email where you'll receive emails
      from: "noreply@teamc.blui.co", // your website email address here
      subject: `${req.body.subject}`,
      html: `<!DOCTYPE html>
      <html lang="en">
      <head>
        <meta charset="UTF-8">
        <title>${req.body.subject}</title>
      </head>
      <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; font-size: 16px; line-height: 1.5;">
        <table role="presentation" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" style="padding: 20px;">
              <h1 style="margin: 0; padding: 0; max-width: 300px;"><svg version="1.1" id="Artboard_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
              viewBox="0 0 400 100" style="enable-background:new 0 0 400 100;" xml:space="preserve">
           <style type="text/css">
             .st0{fill:#003A96;}
             .st1{fill:#009CA6;}
           </style>
           <g>
             <g>
               <path class="st0" d="M46,57.4v1.5h-4.5v3.6h4V64h-4v4.3h-2v-11H46z"/>
               <path class="st0" d="M50.6,68.5c-2.5,0-3.9-1.4-3.9-4.2c0-2.7,1.4-4.2,3.9-4.2c2.5,0,3.9,1.4,3.9,4.2
                 C54.6,67.1,53.2,68.5,50.6,68.5z M52.5,64.3c0-1.8-0.6-2.8-1.9-2.8c-1.3,0-1.9,1.1-1.9,2.8c0,1.8,0.6,2.8,1.9,2.8
                 C52,67.2,52.5,66,52.5,64.3z"/>
               <path class="st0" d="M61.9,68.3L61.5,67c-0.2,0.9-1,1.5-2.4,1.5c-1.1,0-1.8-0.4-2.2-1c-0.5-0.7-0.5-1.5-0.5-2.5v-4.7h1.9v4.8
                 c0,0.5,0,0.9,0.3,1.3c0.3,0.4,0.7,0.6,1.3,0.6c1.3,0,1.5-1.1,1.5-1.8v-4.9h1.9v8H61.9z"/>
               <path class="st0" d="M67.2,60.3l0.4,1.4c0.3-0.9,1-1.5,2.4-1.5c1.2,0,1.9,0.5,2.3,1.1c0.5,0.7,0.5,1.5,0.5,2.4v4.7h-1.9v-4.8
                 c0-0.5,0-0.9-0.3-1.3c-0.3-0.4-0.6-0.6-1.3-0.6c-1.4,0-1.6,1.1-1.6,1.8v4.9h-1.9v-8H67.2z"/>
               <path class="st0" d="M82.1,57v11.4h-1.5L80.3,67c-0.2,0.9-1.1,1.5-2.4,1.5c-2.2,0-3.4-1.7-3.4-4.1c0-2.5,1.2-4.2,3.5-4.2
                 c1.2,0,1.9,0.4,2.2,1.4V57H82.1z M76.5,64.3c0,1.6,0.5,2.7,1.9,2.7s1.9-1.2,1.9-2.7c0-1.6-0.5-2.7-1.9-2.7S76.5,62.7,76.5,64.3z"
                 />
               <path class="st0" d="M86.6,58c0,0.6-0.5,1.1-1.2,1.1c-0.7,0-1.2-0.5-1.2-1.1s0.5-1.1,1.2-1.1C86.1,56.9,86.6,57.4,86.6,58z
                  M86.4,60.3v1.3v6.7h-1.9v-8H86.4z"/>
               <path class="st0" d="M90.2,60.3l0.4,1.4c0.3-0.9,1-1.5,2.4-1.5c1.2,0,1.9,0.5,2.3,1.1c0.5,0.7,0.5,1.5,0.5,2.4v4.7h-1.9v-4.8
                 c0-0.5,0-0.9-0.3-1.3c-0.3-0.4-0.6-0.6-1.3-0.6c-1.4,0-1.6,1.1-1.6,1.8v4.9h-1.9v-8H90.2z"/>
               <path class="st0" d="M101.1,65.3c-0.4,0-0.8,0-1.1-0.1c-0.2,0.1-0.4,0.3-0.4,0.7c0,0.6,0.6,0.6,1.1,0.6h1.4c1,0,3.2,0,3.2,2.2
                 c0,1.8-1.4,2.7-4.3,2.7c-2.6,0-3.7-0.7-3.7-1.9c0-1.4,1.5-1.6,1.6-1.6c0,0-1-0.4-1-1.4c0-1,1-1.4,1.5-1.5
                 c-0.9-0.4-1.4-1.2-1.4-2.2c0-1.6,1.3-2.6,3.3-2.6c1,0,1.8,0.2,2.4,0.7c0.4-1,1.2-1,1.7-1h0.2v1.6c0,0-0.3-0.1-0.6-0.1
                 c-0.4,0-0.6,0.1-0.7,0.1c0.2,0.4,0.4,0.8,0.4,1.3C104.4,64.3,103.1,65.3,101.1,65.3z M99.5,68c0,0-0.6,0.3-0.6,1c0,0.7,0.6,1,2,1
                 c1.5,0,2.4-0.4,2.4-1.1c0-0.8-1-0.8-1.5-0.8H99.5z M102.4,62.7c0-0.8-0.5-1.3-1.3-1.3c-0.8,0-1.3,0.4-1.3,1.3
                 c0,0.8,0.5,1.3,1.3,1.3C101.9,64,102.4,63.5,102.4,62.7z"/>
               <path class="st0" d="M113.5,57.4l3.5,8.4l3.4-8.4h2.5v11h-1.9v-8.6l-3.2,8H116l-3.2-8v8.6H111v-11H113.5z"/>
               <path class="st0" d="M131.6,68.1c0,0-1.1,0.4-2.5,0.4c-1.5,0-2.5-0.4-3.1-1c-0.7-0.7-1.1-1.8-1.1-3.1c0-2.6,1.3-4.3,3.7-4.3
                 c1.3,0,2.3,0.5,2.9,1.4s0.6,2.1,0.6,3.4h-5.2c0,1.4,0.8,2.2,2.3,2.2c1.1,0,2.3-0.4,2.3-0.4h0.1V68.1z M130.1,63.6
                 c0-1.3-0.4-2.1-1.5-2.1c-1.1,0-1.6,0.8-1.7,2.1H130.1z"/>
               <path class="st0" d="M135.4,60.3l0.4,1.4c0.2-0.9,0.9-1.5,2.3-1.5c1.4,0,2.1,0.6,2.4,1.6c0.4-1.1,1.1-1.6,2.3-1.6
                 c1.3,0,1.9,0.6,2.2,1.1c0.4,0.7,0.4,1.5,0.4,2.4v4.7h-1.9v-4.8c0-0.5,0-0.9-0.2-1.3c-0.2-0.3-0.6-0.6-1.2-0.6
                 c-1.3,0-1.5,1.1-1.5,1.8v4.9h-1.9v-4.8c0-0.5,0-0.9-0.2-1.3c-0.2-0.3-0.6-0.6-1.2-0.6c-1.3,0-1.5,1.1-1.5,1.8v4.9h-1.9v-8H135.4z"
                 />
               <path class="st0" d="M149.7,57v4.7c0.3-0.9,1-1.5,2.4-1.5c2.1,0,3.4,1.5,3.4,4.1c0,2.7-1.3,4.2-3.6,4.2c-1.2,0-2-0.4-2.4-1.4
                 l-0.5,1.2h-1.2V57H149.7z M149.7,64.4c0,1.6,0.5,2.8,1.9,2.8c1.4,0,1.9-1.2,1.9-2.8c0-1.6-0.5-2.7-1.9-2.7
                 C150.2,61.6,149.7,62.7,149.7,64.4z"/>
               <path class="st0" d="M163.6,68.1c0,0-1.1,0.4-2.5,0.4c-1.5,0-2.5-0.4-3.1-1c-0.7-0.7-1.1-1.8-1.1-3.1c0-2.6,1.3-4.3,3.7-4.3
                 c1.3,0,2.3,0.5,2.9,1.4s0.6,2.1,0.6,3.4h-5.2c0,1.4,0.8,2.2,2.3,2.2c1.1,0,2.3-0.4,2.3-0.4h0.1V68.1z M162.1,63.6
                 c0-1.3-0.4-2.1-1.5-2.1c-1.1,0-1.6,0.8-1.7,2.1H162.1z"/>
               <path class="st0" d="M170.5,62c0,0-0.4-0.1-0.8-0.1c-1.7,0-1.7,1.3-1.7,1.9v4.5H166v-8h1.5l0.4,1.6c0.3-1.3,1.3-1.7,2.5-1.7h0.2
                 V62z"/>
               <path class="st0" d="M172.5,66.4l-1.1,4.1H170l0.6-4.1H172.5z"/>
               <path class="st0" d="M181.2,57.4l3.5,8.4l3.4-8.4h2.5v11h-1.9v-8.6l-3.2,8h-1.7l-3.2-8v8.6h-1.7v-11H181.2z"/>
               <path class="st0" d="M193.2,60.5c0,0,1-0.4,2.6-0.4c1.2,0,2.3,0.2,2.8,1c0.4,0.6,0.4,1.4,0.4,2.2v2.7c0,0.3,0,0.9,0.7,0.9
                 c0.2,0,0.4-0.1,0.4-0.1v1.3c0,0-0.4,0.1-1,0.1c-1,0-1.7-0.4-1.8-1.4c-0.3,0.9-1.2,1.5-2.4,1.5c-1.6,0-2.6-1-2.6-2.4
                 c0-1.7,1.4-2.6,4-2.6c0.5,0,0.8,0,0.8,0c0-0.7,0-1-0.2-1.3c-0.2-0.3-0.6-0.5-1.4-0.5c-1.2,0-2.2,0.4-2.2,0.4h-0.1V60.5z M195.5,67
                 c0.6,0,1.1-0.3,1.4-0.8c0.3-0.5,0.3-1.1,0.3-1.4v-0.2h-0.4c-0.8,0-2.3,0-2.3,1.3C194.4,66.6,194.8,67,195.5,67z"/>
               <path class="st0" d="M206.5,62h-0.1c0,0-1-0.4-1.9-0.4c-0.8,0-1.2,0.3-1.2,0.7c0,1.2,3.6,0.9,3.6,3.6c0,1.7-1.4,2.5-3.3,2.5
                 c-1.4,0-2.3-0.4-2.3-0.4v-1.5h0.1c0,0,1.2,0.5,2.2,0.5c0.9,0,1.3-0.4,1.3-0.8c0-1.4-3.6-1-3.6-3.6c0-1.5,1.1-2.4,3.2-2.4
                 c1.1,0,2,0.3,2,0.3V62z"/>
               <path class="st0" d="M213.3,62h-0.1c0,0-1-0.4-1.9-0.4c-0.8,0-1.2,0.3-1.2,0.7c0,1.2,3.6,0.9,3.6,3.6c0,1.7-1.4,2.5-3.3,2.5
                 c-1.4,0-2.3-0.4-2.3-0.4v-1.5h0.1c0,0,1.2,0.5,2.2,0.5c0.9,0,1.3-0.4,1.3-0.8c0-1.4-3.6-1-3.6-3.6c0-1.5,1.1-2.4,3.2-2.4
                 c1.1,0,2,0.3,2,0.3V62z"/>
               <path class="st0" d="M228.1,62.5v5.4c0,0-1.4,0.6-3.4,0.6c-3.4,0-5.7-1.8-5.7-5.5c0-3.9,2.4-5.7,5.9-5.7c1.6,0,2.8,0.4,2.8,0.4
                 v1.5h-0.1c0,0-1.1-0.5-2.6-0.5c-2.4,0-3.9,1.3-3.9,4.1s1.5,4.2,3.6,4.2c0.8,0,1.4-0.2,1.4-0.2V64h-2v-1.5H228.1z"/>
               <path class="st0" d="M236.5,68.1c0,0-1.1,0.4-2.5,0.4c-1.5,0-2.5-0.4-3.1-1c-0.7-0.7-1.1-1.8-1.1-3.1c0-2.6,1.3-4.3,3.7-4.3
                 c1.3,0,2.3,0.5,2.9,1.4c0.6,0.9,0.6,2.1,0.6,3.4h-5.2c0,1.4,0.8,2.2,2.3,2.2c1.1,0,2.3-0.4,2.3-0.4h0.1V68.1z M235,63.6
                 c0-1.3-0.4-2.1-1.5-2.1c-1.1,0-1.6,0.8-1.7,2.1H235z"/>
               <path class="st0" d="M240.4,60.3l0.4,1.4c0.3-0.9,1-1.5,2.4-1.5c1.2,0,1.9,0.5,2.3,1.1c0.5,0.7,0.5,1.5,0.5,2.4v4.7H244v-4.8
                 c0-0.5,0-0.9-0.3-1.3c-0.3-0.4-0.6-0.6-1.3-0.6c-1.4,0-1.6,1.1-1.6,1.8v4.9h-1.9v-8H240.4z"/>
               <path class="st0" d="M254.4,68.1c0,0-1.1,0.4-2.5,0.4c-1.5,0-2.5-0.4-3.1-1c-0.7-0.7-1.1-1.8-1.1-3.1c0-2.6,1.3-4.3,3.7-4.3
                 c1.3,0,2.3,0.5,2.9,1.4c0.6,0.9,0.6,2.1,0.6,3.4h-5.2c0,1.4,0.8,2.2,2.3,2.2c1.1,0,2.3-0.4,2.3-0.4h0.1V68.1z M252.9,63.6
                 c0-1.3-0.4-2.1-1.5-2.1c-1.1,0-1.6,0.8-1.7,2.1H252.9z"/>
               <path class="st0" d="M261.3,62c0,0-0.4-0.1-0.8-0.1c-1.7,0-1.7,1.3-1.7,1.9v4.5h-1.9v-8h1.5l0.4,1.6c0.3-1.3,1.3-1.7,2.5-1.7h0.2
                 V62z"/>
               <path class="st0" d="M262.7,60.5c0,0,1-0.4,2.6-0.4c1.2,0,2.3,0.2,2.8,1c0.4,0.6,0.4,1.4,0.4,2.2v2.7c0,0.3,0,0.9,0.7,0.9
                 c0.2,0,0.4-0.1,0.4-0.1v1.3c0,0-0.4,0.1-1,0.1c-1,0-1.7-0.4-1.8-1.4c-0.3,0.9-1.2,1.5-2.4,1.5c-1.6,0-2.6-1-2.6-2.4
                 c0-1.7,1.4-2.6,4-2.6c0.5,0,0.8,0,0.8,0c0-0.7,0-1-0.2-1.3c-0.2-0.3-0.6-0.5-1.4-0.5c-1.2,0-2.2,0.4-2.2,0.4h-0.1V60.5z M265.1,67
                 c0.6,0,1.1-0.3,1.4-0.8c0.3-0.5,0.3-1.1,0.3-1.4v-0.2h-0.4c-0.8,0-2.3,0-2.3,1.3C263.9,66.6,264.3,67,265.1,67z"/>
               <path class="st0" d="M273.1,57v3.3v8.1h-1.9V57H273.1z"/>
               <path class="st0" d="M283.1,57.4c0.8,0,1.8,0,2.6,0.5c0.7,0.4,1.2,1.1,1.2,2.2c0,2.2-1.9,2.4-1.9,2.4s2.6,0.1,2.6,2.7
                 c0,1.3-0.6,2-1.4,2.5c-1,0.6-2.1,0.6-3,0.6h-3.6v-11H283.1z M281.5,61.9h1.3c0.4,0,0.9,0,1.3-0.2c0.4-0.2,0.7-0.6,0.7-1.3
                 c0-0.7-0.3-1.1-0.7-1.3c-0.4-0.2-0.9-0.2-1.3-0.2h-1.3V61.9z M281.5,66.9h1.6c0.5,0,1.1,0,1.6-0.3c0.5-0.3,0.8-0.8,0.8-1.5
                 c0-0.7-0.3-1.2-0.8-1.5c-0.6-0.3-1.1-0.3-1.6-0.3h-1.6V66.9z"/>
               <path class="st0" d="M293.7,62c0,0-0.4-0.1-0.8-0.1c-1.7,0-1.7,1.3-1.7,1.9v4.5h-1.9v-8h1.5l0.4,1.6c0.3-1.3,1.3-1.7,2.5-1.7h0.2
                 V62z"/>
               <path class="st0" d="M297.3,58c0,0.6-0.5,1.1-1.2,1.1c-0.7,0-1.2-0.5-1.2-1.1s0.5-1.1,1.2-1.1C296.8,56.9,297.3,57.4,297.3,58z
                  M297.1,60.3v1.1v6.9h-1.9v-8H297.1z"/>
               <path class="st0" d="M302.6,65.3c-0.4,0-0.8,0-1.1-0.1c-0.2,0.1-0.4,0.3-0.4,0.7c0,0.6,0.6,0.6,1.1,0.6h1.4c1,0,3.2,0,3.2,2.2
                 c0,1.8-1.4,2.7-4.3,2.7c-2.6,0-3.7-0.7-3.7-1.9c0-1.4,1.5-1.6,1.6-1.6c0,0-1-0.4-1-1.4c0-1,1-1.4,1.5-1.5
                 c-0.9-0.4-1.4-1.2-1.4-2.2c0-1.6,1.3-2.6,3.3-2.6c1,0,1.8,0.2,2.4,0.7c0.4-1,1.2-1,1.7-1h0.2v1.6c0,0-0.3-0.1-0.6-0.1
                 c-0.4,0-0.6,0.1-0.7,0.1c0.2,0.4,0.4,0.8,0.4,1.3C305.9,64.3,304.6,65.3,302.6,65.3z M300.9,68c0,0-0.6,0.3-0.6,1c0,0.7,0.6,1,2,1
                 c1.5,0,2.4-0.4,2.4-1.1c0-0.8-1-0.8-1.5-0.8H300.9z M303.9,62.7c0-0.8-0.5-1.3-1.3-1.3c-0.8,0-1.3,0.4-1.3,1.3
                 c0,0.8,0.5,1.3,1.3,1.3C303.4,64,303.9,63.5,303.9,62.7z"/>
               <path class="st0" d="M310.3,57v4.6c0.3-0.8,0.9-1.4,2.3-1.4c1.3,0,2,0.5,2.3,1.1c0.5,0.7,0.5,1.5,0.5,2.4v4.7h-1.9v-4.8
                 c0-0.5,0-0.9-0.3-1.3c-0.3-0.4-0.6-0.6-1.3-0.6c-1.4,0-1.6,1.1-1.6,1.8v4.9h-1.9V57H310.3z"/>
               <path class="st0" d="M317.8,60.5c0,0,1-0.4,2.6-0.4c1.2,0,2.3,0.2,2.8,1c0.4,0.6,0.4,1.4,0.4,2.2v2.7c0,0.3,0,0.9,0.7,0.9
                 c0.2,0,0.4-0.1,0.4-0.1v1.3c0,0-0.4,0.1-1,0.1c-1,0-1.7-0.4-1.8-1.4c-0.3,0.9-1.2,1.5-2.4,1.5c-1.6,0-2.6-1-2.6-2.4
                 c0-1.7,1.4-2.6,4-2.6c0.5,0,0.8,0,0.8,0c0-0.7,0-1-0.2-1.3c-0.2-0.3-0.6-0.5-1.4-0.5c-1.2,0-2.2,0.4-2.2,0.4h-0.1V60.5z M320.1,67
                 c0.6,0,1.1-0.3,1.4-0.8c0.3-0.5,0.3-1.1,0.3-1.4v-0.2h-0.4c-0.8,0-2.3,0-2.3,1.3C319,66.6,319.3,67,320.1,67z"/>
               <path class="st0" d="M327.7,60.3l0.4,1.4c0.2-0.9,0.9-1.5,2.3-1.5c1.4,0,2.1,0.6,2.4,1.6c0.4-1.1,1.1-1.6,2.3-1.6
                 c1.3,0,1.9,0.6,2.2,1.1c0.4,0.7,0.4,1.5,0.4,2.4v4.7h-1.9v-4.8c0-0.5,0-0.9-0.2-1.3c-0.2-0.3-0.6-0.6-1.2-0.6
                 c-1.3,0-1.5,1.1-1.5,1.8v4.9h-1.9v-4.8c0-0.5,0-0.9-0.2-1.3c-0.2-0.3-0.6-0.6-1.2-0.6c-1.3,0-1.5,1.1-1.5,1.8v4.9h-1.9v-8H327.7z"
                 />
             </g>
             <g>
               <path class="st0" d="M45.4,29.9c1.4,0,3,0,4.3,0.8c1.1,0.7,2,1.8,2,3.7c0,3.6-3.2,4-3.2,4s4.3,0.2,4.3,4.5c0,2.1-1,3.4-2.3,4.2
                 c-1.6,1-3.5,1-5,1h-6V29.9H45.4z M42.7,37.5H45c0.7,0,1.5,0,2.2-0.4c0.7-0.4,1.2-1.1,1.2-2.2c0-1.1-0.5-1.8-1.2-2.2
                 c-0.7-0.4-1.5-0.4-2.2-0.4h-2.2V37.5z M42.7,45.9h2.6c0.8,0,1.8,0,2.7-0.5c0.9-0.5,1.3-1.3,1.3-2.4S49,41,48.1,40.5
                 c-0.9-0.5-1.9-0.5-2.7-0.5h-2.6V45.9z"/>
               <path class="st0" d="M62.7,37.7c0,0-0.7-0.1-1.4-0.1c-2.9,0-2.9,2.1-2.9,3.2v7.5h-3.2V34.8h2.4l0.6,2.6c0.5-2.2,2.1-2.8,4.1-2.8
                 h0.3V37.7z"/>
               <path class="st0" d="M68.2,31c0,1.1-0.8,1.9-1.9,1.9c-1.1,0-1.9-0.8-1.9-1.9c0-1.1,0.8-1.9,1.9-1.9C67.4,29.1,68.2,30,68.2,31z
                  M67.8,34.8v4v9.4h-3.2V34.8H67.8z"/>
               <path class="st0" d="M76.5,43.2c-0.7,0-1.3,0-1.8-0.2c-0.3,0.2-0.7,0.5-0.7,1.1c0,1,1.1,1,1.8,1H78c1.7,0,5.3,0,5.3,3.6
                 c0,2.9-2.3,4.5-7.2,4.5c-4.3,0-6.2-1.2-6.2-3.2c0-2.4,2.5-2.7,2.6-2.7c-0.1,0-1.7-0.6-1.7-2.3c0-1.7,1.7-2.3,2.5-2.5
                 c-1.5-0.7-2.3-2-2.3-3.7c0-2.6,2.1-4.3,5.5-4.3c1.7,0,3,0.4,4,1.2c0.6-1.7,2-1.7,2.8-1.7h0.3v2.6c0,0-0.5-0.1-1.1-0.1
                 c-0.6,0-0.9,0.1-1.1,0.2c0.4,0.6,0.6,1.3,0.6,2.1C82,41.5,79.8,43.2,76.5,43.2z M73.7,47.7c0,0-0.9,0.5-0.9,1.6
                 c0,1.2,1,1.7,3.4,1.7c2.6,0,3.9-0.6,3.9-1.9c0-1.4-1.7-1.4-2.4-1.4H73.7z M78.7,38.8c0-1.4-0.8-2.1-2.2-2.1s-2.2,0.7-2.2,2.1
                 s0.8,2.1,2.2,2.1S78.7,40.2,78.7,38.8z"/>
               <path class="st0" d="M88.8,29.2v7.7c0.4-1.4,1.5-2.4,3.8-2.4c2.1,0,3.3,0.8,3.9,1.8c0.8,1.1,0.8,2.4,0.8,4v7.9h-3.2v-7.9
                 c0-0.8,0-1.6-0.4-2.2c-0.4-0.6-1.1-1-2.1-1c-2.4,0-2.7,1.8-2.7,2.9v8.2h-3.2v-19H88.8z"/>
               <path class="st0" d="M100.8,35.2c0,0,1.7-0.6,4.3-0.6c2,0,3.8,0.3,4.7,1.7c0.7,1,0.7,2.4,0.7,3.6v4.6c0,0.6,0,1.6,1.1,1.6
                 c0.4,0,0.7-0.1,0.7-0.1v2.2c0,0-0.7,0.2-1.7,0.2c-1.7,0-2.8-0.6-3-2.4c-0.5,1.6-2,2.4-3.9,2.4c-2.6,0-4.3-1.6-4.3-4
                 c0-2.9,2.4-4.3,6.6-4.3c0.8,0,1.3,0,1.3,0c0-1.1,0-1.7-0.4-2.2c-0.4-0.6-1-0.9-2.4-0.9c-1.9,0-3.6,0.7-3.6,0.7h-0.2V35.2z
                  M104.7,46c0.9,0,1.8-0.4,2.3-1.3c0.4-0.8,0.4-1.8,0.4-2.3v-0.3h-0.7c-1.4,0-3.8,0-3.8,2.1C102.8,45.3,103.5,46,104.7,46z"/>
               <path class="st0" d="M116.9,34.8l0.6,2.3c0.4-1.5,1.5-2.6,3.9-2.6c2.3,0,3.5,1,4.1,2.7c0.6-1.8,1.9-2.7,3.9-2.7
                 c2.1,0,3.2,0.9,3.8,1.8c0.7,1.1,0.7,2.4,0.7,4v7.9h-3.2v-7.9c0-0.8,0-1.6-0.4-2.2c-0.3-0.5-0.9-1-2-1c-2.2,0-2.5,1.8-2.5,2.9v8.2
                 h-3.2v-7.9c0-0.8,0-1.6-0.4-2.2c-0.3-0.5-0.9-1-2-1c-2.2,0-2.5,1.8-2.5,2.9v8.2h-3.2V34.8H116.9z"/>
               <path class="st0" d="M143.5,35.2c0,0,1.7-0.6,4.3-0.6c2,0,3.8,0.3,4.7,1.7c0.7,1,0.7,2.4,0.7,3.6v4.6c0,0.6,0,1.6,1.1,1.6
                 c0.4,0,0.7-0.1,0.7-0.1v2.2c0,0-0.7,0.2-1.7,0.2c-1.7,0-2.8-0.6-3-2.4c-0.5,1.6-2,2.4-3.9,2.4c-2.6,0-4.3-1.6-4.3-4
                 c0-2.9,2.4-4.3,6.6-4.3c0.8,0,1.3,0,1.3,0c0-1.1,0-1.7-0.4-2.2c-0.4-0.6-1-0.9-2.4-0.9c-1.9,0-3.6,0.7-3.6,0.7h-0.2V35.2z
                  M147.3,46c0.9,0,1.8-0.4,2.3-1.3c0.4-0.8,0.4-1.8,0.4-2.3v-0.3h-0.7c-1.4,0-3.8,0-3.8,2.1C145.5,45.3,146.1,46,147.3,46z"/>
               <path class="st0" d="M159.6,34.8l0.6,2.3c0.4-1.6,1.7-2.6,4-2.6c2.1,0,3.2,0.8,3.9,1.8c0.8,1.1,0.8,2.4,0.8,4v7.9h-3.2v-7.9
                 c0-0.8,0-1.6-0.4-2.2c-0.4-0.6-1.1-1-2.1-1c-2.4,0-2.7,1.8-2.7,2.9v8.2h-3.2V34.8H159.6z"/>
               <path class="st0" d="M184,29.2v19h-2.5l-0.6-2.2c-0.4,1.5-1.8,2.5-4,2.5c-3.7,0-5.7-2.8-5.7-6.9c0-4.1,2-7.1,5.9-7.1
                 c1.9,0,3.2,0.7,3.7,2.3v-7.6H184z M174.6,41.5c0,2.7,0.9,4.5,3.1,4.5s3.1-1.9,3.1-4.6S180,37,177.8,37S174.6,38.9,174.6,41.5z"/>
               <path class="st0" d="M195.6,29.9l3.6,14.2l3.7-13.8h3.3l3.7,13.8l3.6-14.2h2.8l-5,18.3H208l-3.7-13.9l-3.8,13.9h-3.3l-5-18.3
                 C192.2,29.9,195.6,29.9,195.6,29.9z"/>
               <path class="st0" d="M222.9,48.6c-4.1,0-6.5-2.4-6.5-7c0-4.6,2.4-7.1,6.6-7.1c4.1,0,6.5,2.4,6.5,7
                 C229.5,46.1,227.1,48.6,222.9,48.6z M226.1,41.5c0-3-1-4.7-3.1-4.7c-2.2,0-3.1,1.9-3.1,4.7c0,3,1,4.7,3.1,4.7
                 C225.2,46.3,226.1,44.4,226.1,41.5z"/>
               <path class="st0" d="M234.6,34.8l0.6,2.3c0.4-1.5,1.5-2.6,3.9-2.6c2.3,0,3.5,1,4.1,2.7c0.6-1.8,1.9-2.7,3.9-2.7
                 c2.1,0,3.2,0.9,3.8,1.8c0.7,1.1,0.7,2.4,0.7,4v7.9h-3.2v-7.9c0-0.8,0-1.6-0.4-2.2c-0.3-0.5-0.9-1-2-1c-2.2,0-2.5,1.8-2.5,2.9v8.2
                 h-3.2v-7.9c0-0.8,0-1.6-0.4-2.2c-0.3-0.5-0.9-1-2-1c-2.2,0-2.5,1.8-2.5,2.9v8.2h-3.2V34.8H234.6z"/>
               <path class="st0" d="M265.1,47.8c0,0-1.8,0.6-4.2,0.6c-2.6,0-4.1-0.7-5.1-1.7c-1.2-1.2-1.8-2.9-1.8-5.1c0-4.4,2.2-7.1,6.2-7.1
                 c2.2,0,3.8,0.8,4.8,2.3c0.9,1.5,0.9,3.5,0.9,5.6h-8.6c0,2.3,1.3,3.6,3.8,3.6c1.9,0,3.8-0.7,3.8-0.7h0.2V47.8z M262.6,40.2
                 c0-2.2-0.7-3.5-2.5-3.5c-1.9,0-2.6,1.3-2.8,3.5H262.6z"/>
               <path class="st0" d="M271.1,34.8l0.6,2.3c0.4-1.6,1.7-2.6,4-2.6c2.1,0,3.2,0.8,3.9,1.8c0.8,1.1,0.8,2.4,0.8,4v7.9h-3.2v-7.9
                 c0-0.8,0-1.6-0.4-2.2c-0.4-0.6-1.1-1-2.1-1c-2.4,0-2.7,1.8-2.7,2.9v8.2h-3.2V34.8H271.1z"/>
               <path class="st0" d="M282.8,35.8l1-6.5h3l-1.8,6.5H282.8z"/>
               <path class="st0" d="M296.4,37.7h-0.2c0,0-1.7-0.7-3.2-0.7c-1.4,0-2,0.5-2,1.2c0,2.1,6,1.5,6,6.1c0,2.8-2.3,4.1-5.5,4.1
                 c-2.3,0-3.8-0.6-3.8-0.6v-2.6h0.2c0,0,2,0.8,3.7,0.8c1.5,0,2.1-0.6,2.1-1.4c0-2.4-6-1.7-6-6.1c0-2.4,1.9-4,5.3-4
                 c1.9,0,3.3,0.5,3.3,0.5V37.7z"/>
               <path class="st0" d="M309.3,29.9v7.5h8.1v-7.5h3.3v18.3h-3.3v-8.3h-8.1v8.3H306V29.9H309.3z"/>
               <path class="st0" d="M330,48.6c-4.1,0-6.5-2.4-6.5-7c0-4.6,2.4-7.1,6.6-7.1c4.1,0,6.5,2.4,6.5,7C336.6,46.1,334.2,48.6,330,48.6z
                  M333.2,41.5c0-3-1-4.7-3.1-4.7c-2.2,0-3.1,1.9-3.1,4.7c0,3,1,4.7,3.1,4.7C332.3,46.3,333.2,44.4,333.2,41.5z"/>
               <path class="st0" d="M347.1,37.7h-0.2c0,0-1.7-0.7-3.2-0.7c-1.4,0-2,0.5-2,1.2c0,2.1,6,1.5,6,6.1c0,2.8-2.3,4.1-5.5,4.1
                 c-2.3,0-3.8-0.6-3.8-0.6v-2.6h0.2c0,0,2,0.8,3.7,0.8c1.5,0,2.1-0.6,2.1-1.4c0-2.4-6-1.7-6-6.1c0-2.4,1.9-4,5.3-4
                 c1.9,0,3.3,0.5,3.3,0.5V37.7z"/>
               <path class="st0" d="M350.4,53V34.8h2.5l0.6,2.2c0.4-1.5,1.8-2.5,4-2.5c3.7,0,5.7,2.8,5.7,6.9c0,4.1-2,7.1-5.9,7.1
                 c-1.9,0-3.2-0.7-3.7-2.3V53H350.4z M359.8,41.5c0-2.7-0.9-4.5-3.1-4.5c-2.3,0-3.1,1.9-3.1,4.6c0,2.7,0.9,4.5,3.1,4.5
                 C358.9,46.1,359.8,44.2,359.8,41.5z"/>
               <path class="st0" d="M369.3,31c0,1.1-0.8,1.9-1.9,1.9c-1.1,0-1.9-0.8-1.9-1.9c0-1.1,0.8-1.9,1.9-1.9C368.6,29.1,369.3,30,369.3,31
                 z M369,34.8V37v11.2h-3.2V34.8H369z"/>
               <path class="st0" d="M380.3,48.1c0,0-1,0.3-2.4,0.3c-1.9,0-3.2-0.5-3.8-1.5c-0.6-0.9-0.6-2-0.6-3.5v-6.1H371V35h2.4v-3.5l3.2-0.9
                 V35h3.7v2.3h-3.7v6c0,0.9,0,1.5,0.3,2c0.3,0.5,0.8,0.6,1.4,0.6c0.9,0,1.8-0.3,1.8-0.3h0.2V48.1z"/>
               <path class="st0" d="M383.2,35.2c0,0,1.7-0.6,4.3-0.6c2,0,3.8,0.3,4.7,1.7c0.7,1,0.7,2.4,0.7,3.6v4.6c0,0.6,0,1.6,1.1,1.6
                 c0.4,0,0.7-0.1,0.7-0.1v2.2c0,0-0.7,0.2-1.7,0.2c-1.7,0-2.8-0.6-3-2.4c-0.5,1.6-2,2.4-3.9,2.4c-2.6,0-4.3-1.6-4.3-4
                 c0-2.9,2.4-4.3,6.6-4.3c0.8,0,1.3,0,1.3,0c0-1.1,0-1.7-0.4-2.2c-0.4-0.6-1-0.9-2.4-0.9c-1.9,0-3.6,0.7-3.6,0.7h-0.2V35.2z
                  M387.1,46c0.9,0,1.8-0.4,2.3-1.3c0.4-0.8,0.4-1.8,0.4-2.3v-0.3h-0.7c-1.4,0-3.8,0-3.8,2.1C385.2,45.3,385.9,46,387.1,46z"/>
               <path class="st0" d="M400,29.2v19h-3.2V34.6v-5.4H400z"/>
             </g>
             <g>
               <polygon class="st1" points="3.8,44.1 0,44.1 0,50.6 0,56.5 3.8,56.5 		"/>
               <polygon class="st1" points="8.2,44.1 8.2,50.6 8.2,56.5 12.1,56.5 12.1,44.1 		"/>
               <polygon class="st1" points="16.5,44.1 16.5,50.6 16.5,56.5 20.3,56.5 20.3,44.1 		"/>
               <polygon class="st1" points="0,41.7 14.3,41.7 28.6,41.7 28.6,38.2 0,38.2 		"/>
               <polygon class="st1" points="14.3,28.7 0,33.4 0,37.1 14.3,32.3 28.6,37.1 28.6,33.4 		"/>
               <path class="st1" d="M28.6,56.2c-0.1,0.2-1.5,2.6-10.6,2.6l-7.4,0C1.2,58.9,0.2,60.1,0,60.2v3.6c0.2-0.1,1.2-1.3,10.6-1.4l7.4,0
                 c9.1,0,10.5-2.4,10.6-2.6V56.2z"/>
               <path class="st1" d="M28.6,62.1c-0.1,0.2-1.5,2.6-10.6,2.6l-7.4,0C1.2,64.8,0.2,66,0,66.1v3.6c0.2-0.1,1.2-1.3,10.6-1.4l7.4,0
                 c9.1,0,10.5-2.4,10.6-2.6V62.1z"/>
               <path class="st1" d="M24.7,44.1v11.8c3.1-0.7,3.8-1.8,3.8-2v-9.8H24.7z"/>
             </g>
           </g>
           </svg></h1>

              <p style="margin-top: 20px; max-width: 600px;">${req.body.fullname}, ${req.body.message}</p>
            </td>
          </tr>
        </table>
      </body>
      </html>`,
    });
  } catch (error) {
     console.log(error);
    return res.status(error.statusCode || 500).json({ error: error.message });
  }

  return res.status(200).json({ success: true });
}

export default sendEmail;