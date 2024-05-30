public class SimpleThreads {
    static void threadMessage(String msg) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, msg);
    }

    private static class MessageLoop implements Runnable {
        @Override
        public void run() {
            String[] message = {"Lenguajes", "de", "programacion", "2024A", "clase", "de", "Hilos", "con", "Runnable"};
            try {
                for (String s : message) {
                    Thread.sleep(4000);
                    threadMessage(s);
                }
            } catch (InterruptedException e) {
                threadMessage("No he terminado de ejecutar!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long tiempoEspera = 1000 * 60 * 60; // 1 hora
        threadMessage("Iniciando loop de Mensajes...");
        long startTime = System.currentTimeMillis();

        // Creamos un arreglo de 5 hilos
        Thread[] hilos = new Thread[5];
        for (int i = 0; i < hilos.length; i++) {
            hilos[i] = new Thread(new MessageLoop());
            hilos[i].start();
        }

        threadMessage("Esperando a que los hilos terminen...");
        boolean algunHiloVivo = true;
        while (algunHiloVivo) {
            algunHiloVivo = false;
            for (Thread hilo : hilos) {
                if (hilo.isAlive()) {
                    algunHiloVivo = true;
                    threadMessage("Esperando a que el hilo " + hilo.getName() + " termine...");
                    hilo.join(1000);
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    if (elapsedTime > tiempoEspera && hilo.isAlive()) {
                        threadMessage("Se está demorando mucho el hilo " + hilo.getName() + "!");
                        hilo.interrupt();
                        hilo.join();
                    }
                }
            }
        }
    }
}
// TAREEA 
//CREAR VARIOS HILOS MINIMO 5 HILOS

// COMO EJECUTAR UN CODIGO EN JAVA CON VISUAL