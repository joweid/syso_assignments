package u3;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class File {
    private int fileLength;
    private Interval byteRange;
    ArrayList<Interval> lockedIntervals;

    public File (int fileLength) {
        this.fileLength = fileLength;
        this.byteRange = new Interval(0, fileLength);
        lockedIntervals = new ArrayList<>();
    }

    public synchronized void lock (int start, int end) throws InterruptedException {
        while (isAlreadyLocked(new Interval(start, end))) {
            wait();
        }
        if (inFileRange(new Interval(start, end), byteRange)) {
            this.lockedIntervals.add(new Interval(start, end));
        }
    }

    public synchronized void unlock (int start, int end) {
        if (inFileRange(new Interval(start, end), byteRange)) {
            removeInterval(new Interval(start, end), this.lockedIntervals);
            notifyAll();
        }
    }

    // Testet, ob Bereich bereits gesperrt ist
    public boolean isAlreadyLocked (Interval interval) {
        for (Interval locked : this.lockedIntervals) {
            if (containsItem(interval, locked)) {
                return true;
            }
        }
        return false;
    }

    // Testet, ob Bereich sich mit gesperrten Bereich überlappt
    public boolean containsItem (Interval interval, Interval existing) {
        for (int i=0; i < interval.getElements().size(); i++) {
            if (existing.getElements().contains(interval.getElements().get(i))) {
                return true;
            }
        }
        return false;
    }

    // Prüft auf Gleichheit von zwei Intervallen
    public boolean equals (Interval interval1, Interval interval2) {
        if (interval1.getElements().size() != interval2.getElements().size()) {
            return false;
        }
        else {
            for (int i=0; i < interval1.getElements().size(); i++) {
                if (interval1.getElements().get(i) != interval2.getElements().get(i)) {
                    return false;
                }
            }
            return true;
        }
    }

    // Entfernt Interval aus gesperrten Intervallen
    public void removeInterval (Interval toRemove, ArrayList<Interval> list) {
        for (int i=0; i < list.size(); i++) {
            if (equals(toRemove, list.get(i))) {
                list.remove(i);
            }
        }
    }

    // Teste, ob Intervalle in der ByteRange der Datei liegen
    public boolean inFileRange (Interval interval, Interval fileRange) {
        for (Integer element : interval.getElements()) {
            if (!fileRange.getElements().contains(element)) {
                return false;
            }
        }
        return true;
    }
}

class Interval {
    private ArrayList<Integer> elements;

    public Interval (int start, int end) {
        elements = new ArrayList<>();

        for (int i=start; i <= end; i++) {
            elements.add(i);
        }
    }

    public ArrayList<Integer> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Integer> interval) {
        this.elements = interval;
    }
}
