#!/bin/sh

port=9001

echo "starting service..."
echo "Using port ${port}."

activator run -Dhttp.port=$port
