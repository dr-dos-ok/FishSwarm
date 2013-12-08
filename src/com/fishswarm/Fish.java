package com.fishswarm;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

class Fish {
	Component tank;
	Image image1;
	Image image2;
	Point location;
	Point velocity;
	Rectangle edges;
	Random random;

	int id = 0;
	String task = "";

	public Fish(int id){
		this.id = id;
	}
	
	public Fish(Image image1, Image image2, Rectangle edges, Component tank) {
		this(0, image1, image2, edges, tank);
	}

	public Fish(int id, Image image1, Image image2, Rectangle edges,
			Component tank) {
		random = new Random(System.currentTimeMillis());
		this.tank = tank;
		this.image1 = image1;
		this.image2 = image2;
		this.edges = edges;
		this.location = new Point(100 + (Math.abs(random.nextInt()) % 300),
				100 + (Math.abs(100 + random.nextInt()) % 100));
		this.velocity = new Point(random.nextInt() % 8, random.nextInt() % 8);
		this.id = id;
	}

	public void swim() {
		if (random.nextInt() % 7 <= 1) {
			velocity.x += random.nextInt() % 4;

			velocity.x = Math.min(velocity.x, 8);
			velocity.x = Math.max(velocity.x, -8);

			velocity.y += random.nextInt() % 4;

			velocity.y = Math.min(velocity.y, 8);
			velocity.y = Math.max(velocity.y, -8);
		}

		location.x += velocity.x;
		location.y += velocity.y;

		if (location.x < edges.x) {
			location.x = edges.x;
			velocity.x = -velocity.x;
		}

		if ((location.x + image1.getWidth(tank)) > (edges.x + edges.width)) {
			location.x = edges.x + edges.width - image1.getWidth(tank);
			velocity.x = -velocity.x;
		}

		if (location.y < edges.y) {
			location.y = edges.y;
			velocity.y = -velocity.y;
		}

		if ((location.y + image1.getHeight(tank)) > (edges.y + edges.height)) {
			location.y = edges.y + edges.height - image1.getHeight(tank);
			velocity.y = -velocity.y;
		}

	}

	public void drawFishImage(Graphics g) {
		if (velocity.x < 0) {
			g.drawImage(image1, location.x, location.y, tank);
			g.setColor(Color.white);
			g.drawString(id + "", location.x, location.y);
		} else {
			g.drawImage(image2, location.x, location.y, tank);
			g.setColor(Color.white);
			g.drawString(id + "", location.x, location.y);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fish other = (Fish) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}