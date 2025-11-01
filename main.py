import tkinter as tk
import random
from multiprocessing import Pool, cpu_count


class LivingEntity:
    def __init__(self, location, cloning_rate, death_rate, mutation_rate):
        global count, entities, unusedSpace, spawnrate, img
        self.location = location
        self.isDead = False
        self.cloning_rate = cloning_rate
        self.death_rate = death_rate
        self.mutation_rate = mutation_rate
        self.color = f"#{round(cloning_rate/3.95):02x}{round(death_rate/3.95):02x}{round(mutation_rate/3.95):02x}"

    

    def try_clone(self):
        global count, entities, unusedSpace, spawnrate, img, new_entities
        if not unusedSpace:
            return 
        if self.isDead: return 
        if random.randint(0,1000) < self.cloning_rate:
            newLocation = random.choice(unusedSpace)
            unusedSpace.remove(newLocation)

            newEntity = LivingEntity(
                newLocation,
                max(0, min(1000, self.cloning_rate + random.randint(-self.mutation_rate, self.mutation_rate))),
                max(1, min(1000, self.death_rate + random.randint(-self.mutation_rate, self.mutation_rate))),
                max(1, min(1000, self.mutation_rate + random.randint(-self.mutation_rate, self.mutation_rate)))
            )
            new_entities.append(newEntity)
        return 

    def update(self):
        global count, entities, unusedSpace, spawnrate, img, new_entities
        if random.randint(0,1000) < self.death_rate:
            self.isDead = True
            new_entities.remove(self)
            unusedSpace.append(self.location)
        self.try_clone()


global count, entities, unusedSpace, spawnrate, img

newUsedSpace = [(x, y) for x in range(1001) for y in range(1001)]
unusedSpace = newUsedSpace.copy()
count = 0
entities = []
new_entities = []
spawnrate = 10



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

def countColors():
    colorCount = {}
    for entity in entities:
        color = entity.color
        if color in colorCount:
            colorCount[color] += 1
        else:
            colorCount[color] = 1
    # Return the color with the highest count
    return max(colorCount, key=colorCount.get) if colorCount else None



def process_entity(entity):
    entity.update()
    return entity


def update():
    
    global count, entities, unusedSpace, spawnrate, img, new_entities
    print(f"Entities: {len(entities)} | Unused Space: {len(unusedSpace)} | Color: {countColors()} | Count: {count}")
    img.put("black", to=(0, 0, w, h))
    for entity in entities:
        img.put(entity.color, to=(entity.location[0], entity.location[1], entity.location[0]+1, entity.location[1]+1))

    if len(unusedSpace) > 0 and random.randint(0,1000) < spawnrate:
        spawnLocation = random.choice(unusedSpace)
        unusedSpace.remove(spawnLocation)
        newEntity = LivingEntity(
            location=spawnLocation,
            cloning_rate=50,
            death_rate=20,
            mutation_rate=10
        )
        entities.append(newEntity)
    a = 0 
    m = len(entities)
    new_entities = entities.copy()
    for entity in entities:
        i = round((a/m)*100)
        a+=1
        print(f"Fortschritt: {i}%", end="\r", flush=True)
        entity.update()
    entities = new_entities.copy()

    count += 1

def UpdateCall():
    try:
        update()
    except Exception as e:
        print(f"Error during update: {e}")
    root.after(50, UpdateCall)

UpdateCall()
root.mainloop()