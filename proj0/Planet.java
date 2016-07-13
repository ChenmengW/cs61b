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
    }

    public void draw() {
        String filename = "./images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, filename);
    }
}
