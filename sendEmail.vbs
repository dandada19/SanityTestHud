Set args = Wscript.Arguments

For Each arg In args
  'Wscript.Echo arg
Next

Dim outobj, mailobj

Set outobj = CreateObject("Outlook.Application")
Set mailobj = outobj.CreateItem(0)

With mailobj
  
  .To = args(0)
  .Cc = args(1)
  .Subject = args(2)
  .HTMLBody= args(3)
  .Display
End With

'Clear the memory
Set outobj = Nothing
Set mailobj = Nothing
