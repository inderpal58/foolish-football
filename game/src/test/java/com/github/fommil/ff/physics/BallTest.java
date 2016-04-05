/*
 * Copyright Samuel Halliday 2010
 * 
 * This file is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this file.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.fommil.ff.physics;

import org.junit.Test;
import org.ode4j.math.DVector3;
import org.ode4j.ode.DBody;
import org.ode4j.ode.DGeom;
import org.ode4j.ode.DSphere;


import java.util.Arrays;

import com.github.fommil.ff.Pitch;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Samuel Halliday
 */
public class BallTest {

	
	@Test
	public void testAfertouch() {
		DummyPhysics physics = new DummyPhysics();
		Ball ball = physics.createBall();
		Collection<Aftertouch> aftertouches = new HashSet<Aftertouch>();
		ball.setAftertouch(true);

		ball.setVelocity(new Velocity(150,150,150));
		
		aftertouches.add(Aftertouch.UP);
		aftertouches.add(Aftertouch.DOWN);
	
		aftertouches.add(Aftertouch.LEFT);
		aftertouches.add(Aftertouch.LEFT);
		

		ball.setAftertouch(aftertouches);
		DBody body =  ball.getGeom().getBody();
				
		assertEquals(body.getForce(), new DVector3(-2.4999999999999996, 2.4999999999999996, 6.0));
		
	}

	
}
