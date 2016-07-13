import java.util.ArrayList;
import java.util.List;

/**
 * Created by varad on 7/13/16.
 */
public class Planet {

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    /** EXTRA : TRACING LOCUS OF PLANETS */
//    public List<Double> xList = new ArrayList<Double>();
//    public List<Double> yList = new ArrayList<Double>();

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;

        /** EXTRA : TRACING LOCUS OF PLANETS */
//        this.xList.add(this.xxPos);
//        this.yList.add(this.yyPos);
    }

    public double calcDistance(Planet p) {
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        double rSquared = (dx * dx) + (dy * dy);
        return Math.sqrt(rSquared);
    }

    public double calcForceExertedBy(Planet p) {
        double G = 6.67 * Math.pow(10, -11);
        double r = calcDistance(p);
        double force = (G * p.mass * this.mass) / (r * r);
        return force;
    }

    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - this.xxPos;
        double r = calcDistance(p);
        double force = calcForceExertedBy(p);
        double forceX = force * dx / r;
        return forceX;
    }

    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - this.yyPos;
        double r = calcDistance(p);
        double force = calcForceExertedBy(p);
        double forceY = force * dy / r;
        return forceY;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double netForceX = 0;
        for (Planet p : planets) {
            if (p == this) {
                continue;
            }
            else {
                netForceX += calcForceExertedByX(p);
            }
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double netForceY = 0;
        for (Planet p : planets) {
            if (p == this) {
                continue;
            }
            else {
                netForceY += calcForceExertedByY(p);
            }
        }
        return netForceY;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / this.mass;
        double aY = fY / this.mass;

        this.xxVel = this.xxVel + (aX * dt);
        this.yyVel = this.yyVel + (aY * dt);

        this.xxPos = this.xxPos + (this.xxVel * dt);
        this.yyPos = this.yyPos + (this.yyVel * dt);

        /** EXTRA : TRACING LOCUS OF PLANETS

          * 1. Blindly tracking every point. Completely dumb way
          *    to go about things. */
//        this.xList.add(this.xxPos);
//        this.yList.add(this.yyPos);

        /** 2. This seemed like a good idea at first, but it
          *    creates issues because some orbit points are
          *    repeated in weird loops. */

//        if (!xList.contains(this.xxPos)) {
//            this.xList.add(this.xxPos);
//        }
//        if (!yList.contains(this.yyPos)) {
//            this.yList.add(this.yyPos);
//        }
    }

    public void draw() {
        String filename = "./images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, filename);
    }
}
