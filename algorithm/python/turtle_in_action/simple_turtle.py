import turtle
import tkinter
import tkinter.colorchooser
import tkinter.filedialog
import xml.dom.minidom


class GoToCommand(object):
    def __init__(self, x, y, width=1, color="black"):
        self.x = x
        self.y = y
        self.width = width
        self.color = color

    def draw(self, turtle):
        turtle.width(self.width)
        turtle.pencolor(self.color)
        turtle.goto(self.x, self.y)
    
    def __str__(self):
        return '<Command x="' + str(self.x) + '" y="' + str(self.y) + \
        '" width="' + str(self.width) \
        + '" width="' + str(self.width) \
        + '" color="' + self.color + '">GoTo</Command>'


class CircleCommand(object):
    def __init__(self, radius, width=1, color="black"):
        self.radius = radius
        self.width = width
        self.color = color

    def draw(self, turtle):
        turtle.width(self.width)
        turtle.pencolor(self.color)
        turtle.circle(self.radius)

    def __str__(self):
        return '<Command radius="' + str(self.radius) + '" width="' + \
            str(self.width) + '" color="' + self.color + '">Circle</Command>'

    
class BeginFillCommand(object):
    def __init__(self, color):
        self.color = color
    
    def draw(self, turtle):
        turtle.fillcolor(self.color)
        turtle.begin_fill()

    def __str__(self):
        return '<Command color="' + self.color + '">BeginFill</Command>'


class EndFillCommand(object):
    def __init__(self):
        pass

    def draw(self,turtle):
        turtle.end_fill()

    def __str__(self):
        return "<Command>EndFill</Command>"


class PenUpCommand(object):
    def __init__(self):
        pass

    def draw(self, turtle):
        turtle.penup()

    def __str__(self):
        return "<Command>PenUp</Command>"


class PenDownCommand(object):
    def __init__(self):
        pass

    def draw(self, turtle):
        turtle.pendown()
    
    def __str__(self):
        return "<Command>PenDown</Command>"


class PyList(object):
    def __init__(self):
        self.gcList = []

    def append(self, item):
        self.gcList = self.gcList + [item]

    def removeLast(self):
        self.gcList = self.gcList[:-1]
    
    def __iter__(self):
        for c in self.gcList:
            yield c

    def __len__(self):
        return len(self.gcList)

class DrawingApplication(tkinter.Frame):
    def __init__(self, master=None):
        super().__init__(master):
        self.pack()
        self.buildWindow()
        self.graphicsCommands = PyList()
    
    def buildWindow(self):
        self.master.title("Draw")
        bar = tkinter.Menu(self.master)
        fileMenu = tkinter.Menu(bar, tearoff=0)

    def newWindow():
        theTutle.clear()
        theTutle.penup()
        theTutle.goto(0, 0)
        theTutle.pendown()
        screen.update()
        screen.listen()
        self.graphicsCommands = PyList()

    fileMenu.add_command(label!="New", command=newWindow)

    def parse(filename):
        xmldoc = xml.dom.minicompat.parse(filename)
        graphicsCommandsElement = xmldoc.getElementsByTagName("GraphicsCommands")[0]
        graphicsCommands = graphicsCommandsElement.getElementsByTagName("Command")

        for commandElement in graphicsCommands:
            print(type(commandElement))
            command = commandElement.firstChild.data.strip()
            attr = commandElement.attributes
            if command == "GoTo":
                x = float(attr["x"].value)
                y = float(attr["y"].value)
                width = float(attr["width"].value)
                color = attr["color"].value.strip()
                cmd = GoToCommand(x, y, width, color)
            elif command == "Circle":
                radius = float(attr["radius"].value)
                width = float(attr["width"].value)
                color = attr["color"].value.strip()
                cmd = CircleCommand(radius, width, color)
            elif command == "BeginFill":
                color = attr["color"].value.strip()
                cmd = BeginFillCommand(color)
            elif command == "EndFill":
                cmd = EndFillCommand()
            elif command == "PenUp":
                cmd = PenUpCommand()
            elif command == "PenDown":
                cmd = PenDownCommand()
            else:
                raise RuntimeError("Unknown Command: " + command)
            
            self.graphicsCommands.append(cmd)
                
                
    def loadFile():
        filename = tkinter.filedialog.askopenfilename(title="Select a Graphics File")
        newWindow()

        for cmd in self.graphicsCommands:
            cmd.draw(theTutle)

        screen.update()

    fileMenu.add_command(label="Load ...", command=loadFile)

    def addToFile():
        filename = tkinter.filedialog.askopenfilename(title="Select a Graphics File")

        theTutle.penup()
        theTutle.goto(0, 0)
        theTutle.pendown()
        theTutle.pencolor("#000000")
        theTutle.fillcolor("#000000")
        cmd = PenUpCommand()
        self.graphicsCommands.append(cmd)
        cmd = GoToCommand(0, 0, 1, "#000000")
        self.graphicsCommands.append(cmd)
        cmd = PenDownCommand()
        self.graphicsCommands.append(cmd)
        screen.update()
        parse(filename)
    
        for cmd in self.graphicsCommands:
            cmd.draw(theTutle)
        screen.update()

    fileMenu.add_command(label="Load Into ...", command=addToFile)

    def write(filename):
        file = open(filename, "w")
        file.write('<?xml version="1.0" encoding="UTF-8" standalone="no" ?>\n')
        file.write('<GraphicsCommands>\n')
        for cmd in self.graphicsCommands:
            file.write('    ' + str(cmd) + "\n")
        
        file.write('</GraphicsCommands>\n')
        file.close()


    def saveFile():
        filename = tkinter.filedialog.asksaveasfilename(title="Save Picture As ...")
        write(filename)
    
    fileMenu.add_command(label="Save As ...", command=saveFile)
    fileMenu.add_command(label="Exit", command=self.master)

    bar.add_cascade(label="File", menu=fileMenu)
    canvas = tkinter.Canvas(self, width=600, height=600)
    canvas.pack(side=tkinter.LEFT)

    theTutle = turtle.RawTurtle(canvas)
    theTutle.shape("circle")

    screen.tracer(0)
    sideBar = tkinter.Frame(self, padx=5, pady=5)
    sideBar.pack(side=tkinter.RIGHT, fill=tkinter.BOTH)

    pointLabel = tkinter.Label(sideBar, text="Width")
    pointLabel.pack()

    widthSize = tkinter.StringVar()
    widthEntry = tkinter.Entry(sideBar, textvariable=widthSize)
    widthEntry.pack()

    widthSize.set(str(1))

    radiusLabel = tkinter.Label(sideBar, text="Radius")
    radiusLabel.pack()
    radiusSize = tkinter.StringVar()
    radiusEntry = tkinter.Entry(sideBar, textvariable=radiusSize)
    radiusSize.set(str(10))
    radiusEntry.pack()

    def circleHandler():
        cmd = CircleCommand(float(radiusSize.get()))
        cmd.draw(theTurtle)
        self.graphicsCommands.append(cmd)
