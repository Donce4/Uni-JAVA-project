======================================================
                 SHAPE EDITOR v1.0
======================================================

An interactive Object-Oriented Vector Graphics Editor.
Built with Java Swing.

------------------------------------------------------
[ HOW TO RUN ]
------------------------------------------------------
* WINDOWS USERS:
  Simply double-click the "ShapeEditor.exe" file.
  (Note: Requires Java JRE 8 or newer installed on your system).

* LINUX / MAC USERS:
  Open your terminal in this folder and run:
  java -jar ShapeEditor.jar

------------------------------------------------------
[ TOOLS & CONTROLS ]
------------------------------------------------------
1. DRAWING SHAPES:
   - Click "Rectangle", "Circle", or "Triangle" in the top toolbar.
   - Click anywhere on the white canvas and DRAG your mouse to draw
     and size the shape dynamically.

2. SELECTING & MOVING:
   - Click "Select" in the toolbar.
   - Click on any shape to select it (a red bounding box will appear).
   - Click and drag the shape to move it around the canvas.
   - MULTI-SELECT: Hold the [CTRL] key while clicking to select
     multiple shapes at the same time.

3. GROUPING SHAPES:
   - Use [CTRL] + Click to select multiple shapes.
   - Click the "Group" button.
   - The shapes are now fused into a single interactive object.
     You can drag the entire cluster together!
   - To separate them, select the group and click "Ungroup".

4. SAVING & LOADING:
   - Click "Save" to export your current canvas to a binary (.bin) file.
   - Click "Load" to restore a previously saved workspace.
   - Try loading the files found in the "examples" folder!

------------------------------------------------------
[ ARCHITECTURE NOTES (For Grading) ]
------------------------------------------------------
This application satisfies all university requirements:
- Create from primitives: Dynamic Factory pattern implementation.
- Display & Transform: Custom paintComponent and dynamic bounds logic.
- Combine/Group: Strict implementation of the Composite Design Pattern.
- Save/Restore: Implemented via Java Object Serialization.

======================================================
