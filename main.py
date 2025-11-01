import tkinter as tk

class LivingEntity:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.alive = True

    def move(self, dx, dy):
        self.x += dx
        self.y += dy

    def death(self):
        self.alive = False


root = tk.Tk()
root.title("Life Simulator")

w,h = 1600,1020

canvas = tk.Canvas(root, width=w, height=h, bg="black")
canvas.pack()


# Schwarzer Hintergrund
img = tk.PhotoImage(width=w, height=h)
canvas.create_image((w/2, h/2), image=img, state="normal")

# Ganze Fläche schwarz setzen
img.put("black", to=(0, 0, w, h))

def set_pixel(x, y, color):
  img.put(color, (x, y))

def on_click(event):
  # Beispiel: roter Pixel beim Klicken
  set_pixel(event.x, event.y, "#ff0000")

canvas.bind("<Button-1>", on_click)

root.mainloop()