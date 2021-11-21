public class Day12 implements Day {
    Command[] commands;

    @Override
    public void prepare(String input) {
        String[] lines = input.split("\n");
        commands = new Command[lines.length];
        for (int i = 0; i < lines.length; i++) {
            commands[i] = new Command(lines[i]);
        }
    }

    Vector dir = new Vector(1, 0);
    Vector pos = new Vector(0, 0);

    @Override
    public String partOne() {
        for (Command c : commands) {
            if (c.command.equals("N")) {
                pos.add(0, 1, c.count);
            } else if (c.command.equals("S")) {
                pos.add(0, -1, c.count);
            } else if (c.command.equals("W")) {
                pos.add(-1, 0, c.count);
            } else if (c.command.equals("E")) {
                pos.add(1, 0, c.count);
            } else if (c.command.equals("L")) {
                dir.rotate(c.count);
            } else if (c.command.equals("R")) {
                dir.rotate(-c.count);
            } else {
                pos.add(dir, c.count);
            }
        }
        return Math.abs(pos.x) + Math.abs(pos.y) + "";
    }

    Vector ship = new Vector(0, 0);
    Vector waypoint = new Vector(10, 1);

    @Override
    public String partTwo() {
        for (Command c : commands) {
            if (c.command.equals("N")) {
                waypoint.add(0, 1, c.count);
            } else if (c.command.equals("S")) {
                waypoint.add(0, -1, c.count);
            } else if (c.command.equals("W")) {
                waypoint.add(-1, 0, c.count);
            } else if (c.command.equals("E")) {
                waypoint.add(1, 0, c.count);
            } else if (c.command.equals("L")) {
                waypoint.rotate(c.count);
            } else if (c.command.equals("R")) {
                waypoint.rotate(-c.count);
            } else {
                ship.add(waypoint.x, waypoint.y, c.count);
            }
        }
        return Math.abs(ship.x) + Math.abs(ship.y) + "";
    }

    class Vector {
        double x, y;

        public Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void rotate(double degrees) {
            double xr = Math.cos(Math.toRadians(degrees)) * x - Math.sin(Math.toRadians(degrees)) * y;
            double yr = Math.sin(Math.toRadians(degrees)) * x + Math.cos(Math.toRadians(degrees)) * y;
            this.x = Math.round(xr * 1e10)/1e10;
            this.y = Math.round(yr * 1e10)/1e10;
        }
        
        public void rotate(double degrees, Vector center){
            add(-center.x, -center.y);
            rotate(degrees);
            add(center);
        }

        public void add(Vector v) {
            add(v.x, v.y);
        }

        public void add(double x, double y) {
            this.x += x;
            this.y += y;
        }

        public void add(Vector v, int count) {
            add(v.x, v.y, count);
        }

        public void add(double x, double y, int count) {
            for (int i = 0; i < count; i++) {
                add(x, y);
            }
        }

        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }

    class Command {
        String command;
        int count;

        public Command(String command) {
            this.command = command.substring(0, 1);
            this.count = Integer.parseInt(command.substring(1, command.length()));
        }
    }
}
