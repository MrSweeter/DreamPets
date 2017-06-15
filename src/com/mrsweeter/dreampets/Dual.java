package com.mrsweeter.dreampets;

import org.bukkit.entity.Entity;

public class Dual {
	
	private Entity A;
	private Entity B;
	
	public Dual(Entity a, Entity b)	{
		A = a;
		B = b;
	}

	public Entity getA() {
		return A;
	}

	public Entity getB() {
		return B;
	}
	
	public boolean isValid(Entity ent)	{
		if (A == ent || B == ent)	{
			return true;
		}
		return false;
	}
}
