package main

import "fmt"
import "net/http"
import "io/ioutil"
import "time"


func main() {

	client := &http.Client{
		Timeout: 1 * time.Second,
	}

	var ok = 0
	start := time.Now()
    Start:

	resp, err := client.Get("http://192.168.64.2:31133/k8s/Dara")
	if err != nil {
		fmt.Println("Connection timeout... ")

	} else {
		ok = 1
	}

	if ok == 0 {
	    time.Sleep(1 * time.Second)
		goto Start
	}
	elapsed := time.Since(start)
	fmt.Printf("The service take %s for ready\n", elapsed)
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	bs := string(body)
	fmt.Println(bs)
}

