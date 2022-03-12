# comp3021lab1

COMP 3021 2021-2022 Spring

Code interview (12/3/2022)

Task 1: How to design and apply lab 2

Most of the methods and fields are supplied from the powerpoint, which are faily simple like setting up new objects for them.
The constructors are simple too, some may requre the use of super(), not big deal. But need to be aware of how to assign fields for different objects.

The main focus would be the insertNote function

How I apply:

First the method check if there is exsiting folder or not, if yes use variable f to store the folder object for use later, if not f will remain null.
If the f is null, which means no folder found, then the method will create a new folder and insert a note immediately, then leave the method.
If the f has folder object, then the method will scan through the note list in the folder, if there is already same note name located, method will return false
and will not insert new note. If there is no note with the same name, the method will return true and then insert the new note.

Difficulity:

At first I was unable to create notes when calling the method, this is because the f.getNotes() would return empty list as there is nothing in the list, so the
solution is when creating the new folder, immediately insert the new note into it.

Task 2: 

How to restructure the code if I want to move a note from a folder to another folder? 
In addition, if a note has been moved, how can I know its all moving history(folder and moved time)? 
Will there be possible failure when I want to move a note? Descirbe your idea.


notebook would need a new class called:

MoveHistory

Fields: 

String oldFolder_name

String newFolder_name

String requiredNote_name

Date move_date

Note class would need a copy constuctor which copy everything with a note as parameter 

This will be used as deep copy of note object

Folder class would need a method to removeNote, which will be used for later function
with the given name, search the note in the note list
and then remove it

New field in the NoteBook class, a list of MoveHistory objects

A new method would be addMoveHistory(MoveHistory mh)

This method will add the MoveHistory object into the MoveHistory list

New method in the NoteBook call moveNote(String oldFolder_name,String requiredNote,String newFolder_name)

First of all, will search through Folder list which match the old folder name

Then search through the note list in the old folder which match the note name

If found, use a varable to store the object of the note, then use the varable to do a deep copy (call copy constructor) and then add to the new folder (using insert note)

If adding is successful, call the removeNote and remove the note in the oldFolder

And for the record, create a MoveHistory object by calling its constructor with parameters oldFolder_name, newFolder_name, and requiredNote_name

The moving date would be the creation of this MoveHistory object.

After creating MoveHistory object, add to the MoveHistory list with addMoveHistory(MoveHistory mh)

FINISHED!

I think there would be a failure if directly move the note, it would be better to make a copy first then remove the one in the old location. 
This is the safest approach from my point of view
