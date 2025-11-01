import tkinter as tk
import random

class LivingEntity:
    def __init__(self, location, cloning_rate, death_rate, mutation_rate):
        self.location = location
        self.isDead = False
        self.cloning_rate = cloning_rate
        self.death_rate = death_rate
        self.mutation_rate = mutation_rate
        self.color = f"#{int(cloning_rate/3.95):02x}{int(death_rate/3.95):02x}{int(mutation_rate/3.95):02x}"

    

    def try_clone(self):
        if self.isDead: return 
        if random.randint(0,1000) < self.cloning_rate:
            newLocation = random.choice(UsedSpace)
            UsedSpace.remove(newLocation)

            newEntity = LivingEntity(
                newLocation,
                max(0, min(1000, self.cloning_rate + random.randint(-self.mutation_rate, self.mutation_rate))),
                max(0, min(1000, self.death_rate + random.randint(-self.mutation_rate, self.mutation_rate))),
                max(0, min(1000, self.mutation_rate + random.randint(-self.mutation_rate, self.mutation_rate)))
            )
            entities.append(newEntity)
        return 
    
    def try_death(self):
        if random.randint(0,1000) < self.death_rate:
            self.isDead = True
            entities.remove(self)
            UsedSpace.append(self.location)
        

    def update(self):
        self.try_death()
        self.try_clone()


global UsedSpace
global count
global entities 

newUsedSpace = [(x, y) for x in range(1001) for y in range(1001)]
count = 0
UsedSpace = newUsedSpace.copy()



root = tk.Tk()
root.title("Life Simulator")

w,h = 1600,1020

canvas = tk.Canvas(root, width=1000, height=1000, bg="black")
canvas.pack(side="left")

canvas2 = tk.Canvas(root, width=600, height=1000, bg="gray")
canvas2.pack(side="right")

img = tk.PhotoImage(width=1000, height=1000)
canvas.create_image((500, 500), image=img, state="normal")

img.put("black", to=(0, 0, w, h))


def update():


    count += 1
    root.after(100, update)

root.mainloop()