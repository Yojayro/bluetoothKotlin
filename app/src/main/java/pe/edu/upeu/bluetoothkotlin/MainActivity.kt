package pe.edu.upeu.bluetoothkotlin

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_ENABLE_BT:Int = 1;
    private val REQUEST_CODE_DISCOVERABLE_BT:Int = 2;

    lateinit var bAdapter:BluetoothAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bAdapter = BluetoothAdapter.getDefaultAdapter()

        if(bAdapter==null){
            blueStatus.text = "Bluetooth no esta en servicio"
        }
        else {
            blueStatus.text = "Bluetooth disponible"
        }
        if (bAdapter.isEnabled){
            blueIv.setImageResource(R.drawable.ic_blue_on)
        }
        else{
            blueIv.setImageResource(R.drawable.ic_blue_off)
        }

        prenderBtn.setOnClickListener {
            if(bAdapter.isEnabled){
                Toast.makeText(this, "Prendido", Toast.LENGTH_LONG).show()
            }
            else{
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT);
            }
        }
        apagarBtn.setOnClickListener {
            if(!bAdapter.isEnabled){
                Toast.makeText(this, "Apagado", Toast.LENGTH_LONG).show()
            }
            else{
               bAdapter.disable()
                blueIv.setImageResource(R.drawable.ic_blue_off)
                Toast.makeText(this, "Apagado Blue", Toast.LENGTH_LONG).show()

            }
        }

        visibleBtn.setOnClickListener {
            if (!bAdapter.isDiscovering){
                Toast.makeText(this, "Buscando dispositivos", Toast.LENGTH_LONG).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, REQUEST_CODE_DISCOVERABLE_BT)

            }
        }
        buscarBtn.setOnClickListener {
             if (bAdapter.isEnabled){
                emparejados.text = "Buscando dispositvos"

                 val devices = bAdapter.bondedDevices
                 for (device in devices){
                     val deviceName = device.name
                     val deviceAddress = device
                     emparejados.append("\nDevice: $deviceName , $device")
                 }
             }
            else{
                 Toast.makeText(this, "Prenda el Blue Primero", Toast.LENGTH_LONG).show()

             }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_CODE_ENABLE_BT ->
                if (resultCode == Activity.RESULT_OK){
                    blueIv.setImageResource(R.drawable.ic_blue_on)
                    Toast.makeText(this, "El Blue esta Prendido", Toast.LENGTH_LONG).show()
                }
            else{
                    Toast.makeText(this, "El Blue no esta disponible", Toast.LENGTH_LONG).show()
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}